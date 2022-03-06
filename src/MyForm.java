import com.formdev.flatlaf.extras.FlatSVGIcon;
import packet.FileParserFactory;
import packet.Packet;
import packetListModel.PacketListViewRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serial;
import java.util.List;

public class MyForm extends JFrame {
    private JPanel panel;
    private JLabel sourceLabel;
    private JLabel destLabel;
    private JLabel direction;
    private JList<Packet> packetListView;
    private JScrollPane scrollView;
    private JLayeredPane listLayeredPane;

    public MyForm() {
        setContentPane(panel);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        packetListView.setCellRenderer(new PacketListViewRenderer());
        packetListView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    updateViews(packetListView.getSelectedValue());
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
        packetListView.setModel(listModel);
    }

    private JMenu createFileMenu() {
        JMenuItem exit, open;
        JMenu file = new JMenu("Файл");
        open = new JMenuItem("Открыть", new FlatSVGIcon("menu-open.svg"));
        open.setIconTextGap(10);
        exit = new JMenuItem(new ExitAction());
        exit.setIconTextGap(10);
        exit.setIcon(new FlatSVGIcon("close.svg"));
        file.add(open);
        file.addSeparator();
        file.add(exit);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
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
    }

    private void createUIComponents() {
        listLayeredPane = new JLayeredPane();
        LoadingPanel loadingPanel = new LoadingPanel();
        listLayeredPane.add(loadingPanel, JLayeredPane.PALETTE_LAYER);
        loadingPanel.setBounds(300, 10, 150, 30);
    }

    static class ExitAction extends AbstractAction {
        @Serial
        private static final long serialVersionUID = 1L;

        ExitAction() {
            putValue(NAME, "Выход");
        }

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
