import com.formdev.flatlaf.extras.FlatSVGIcon;
import packet.FileParserFactory;
import packet.Packet;
import packet.Parser;
import packetListModel.ListRowView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serial;
import java.util.List;

public class MyForm extends JFrame {
    private final DefaultListModel<Packet> packetListViewModel = new DefaultListModel<>();
    private JPanel panel;
    private JLabel sourceLabel;
    private JLabel destLabel;
    private JList<Packet> packetListView;
    private JLayeredPane listLayeredPane;
    private Parser parser;
    private LoadingPanel loadingPanel;

    public MyForm(Parser p) {
        this.parser = p;
        setVisible(true);
        setContentPane(panel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        packetListView.setCellRenderer(new ListRowView());
        packetListView.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateViews(packetListView.getSelectedValue());
            }
        });
        updateParser(parser);
        packetListView.setModel(packetListViewModel);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        setJMenuBar(menuBar);
        //оптимальный размер окна
        pack();
    }

    private void fileParsed(List<Packet> parsedPackets) {
        packetListViewModel.addAll(parsedPackets);
        loadingPanel.stop();
        loadingPanel.setVisible(false);
    }

    public void packetParsed(Packet parsedPacket) {
        packetListViewModel.addElement(parsedPacket);
        loadingPanel.reset();
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

        open.addActionListener(actionEvent -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(parser.getFile());
            packetListViewModel.removeAllElements();
            int res = chooser.showDialog(null, "Открыть файл");
            if (res == JFileChooser.APPROVE_OPTION) {
                File chosenFile = chooser.getSelectedFile();
                parser = FileParserFactory.produce(chosenFile.getPath());
                updateParser(parser);
            }
        });
        return file;
    }

    private void updateParser(Parser parser) {
        parser.setFileParsedListener(this::fileParsed);
        parser.setPacketParsedListener(this::packetParsed);
        parser.run();
    }

    private void updateViews(Packet packet) {
        destLabel.setText(packet.getDestination());
        sourceLabel.setText(packet.getSource());
    }

    private void createUIComponents() {
        listLayeredPane = new JLayeredPane();
        loadingPanel = new LoadingPanel();
        listLayeredPane.add(loadingPanel, JLayeredPane.PALETTE_LAYER);
        loadingPanel.setBounds(320, 10, 130, 30);
        loadingPanel.start();
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
