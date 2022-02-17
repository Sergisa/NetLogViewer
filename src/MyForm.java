import packet.FileParserFactory;
import packet.Packet;
import packetListModel.PacketListViewRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class MyForm extends JFrame {
    private JPanel panel;
    private JLabel sourceLabel;
    private JLabel destLabel;
    private JLabel direction;
    private JList<Packet> packetList;

    public MyForm() {

        setDefaultLookAndFeelDecorated(true);
        setContentPane(panel);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        packetList.setCellRenderer(new PacketListViewRenderer());
        packetList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    updateViews(packetList.getSelectedValue());
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
    }

    public void setPackets(List<Packet> packetsList) {
        DefaultListModel<Packet> listModel = new DefaultListModel<>();
        for (Packet p : packetsList) {
            listModel.addElement(p);
        }
        packetList.setModel(listModel);
    }

    private JMenu createFileMenu() {
        JMenuItem exit, open;
        JMenu file = new JMenu("Файл");
        open = new JMenuItem("Открыть", ImageResizer.getResized(new ImageIcon("src/open.png"), 16, 16));
        open.setIconTextGap(10);
        exit = new JMenuItem(new ExitAction());
        exit.setIconTextGap(10);
        exit.setIcon(ImageResizer.getResized(new ImageIcon("src/close.png"), 16, 16));
        file.add(open);
        file.addSeparator();
        file.add(exit);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Запускаю диалог выбора файла");
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showDialog(null, "Открыть файл");
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    setPackets(FileParserFactory.produce(file.getPath()).getPackets());
                }
            }
        });
        return file;
    }

    private void updateViews(Packet packet) {
        destLabel.setText(packet.getDestination());
        sourceLabel.setText(packet.getSource());
        System.out.println(packet.getType());
    }

    private void createUIComponents() {
    }

    static class ExitAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        ExitAction() {
            putValue(NAME, "Выход");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
