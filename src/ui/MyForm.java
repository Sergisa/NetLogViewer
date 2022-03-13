package ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import packet.Packet;
import packet.parser.FileParserFactory;
import packet.parser.Parser;
import packet.parser.ParserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Serial;

public class MyForm extends JFrame {
    private static ParserTask parserTask;
    private final DefaultListModel<Packet> packetListViewModel = new DefaultListModel<>();
    private JPanel panel;
    private JLabel sourceLabel;
    private JLabel destLabel;
    private JList<Packet> packetListView;
    private JLayeredPane listLayeredPane;
    private JLabel dateLabel;
    private JButton openFileButton;
    private JButton reloadData;
    private Parser parser;
    private LoadingPanel loadingPanel;

    public MyForm() {
        setUndecorated(true);
        setVisible(true);
        setContentPane(panel);
        ComponentDragListener frameDragListener = new ComponentDragListener(this);
        addMouseListener(frameDragListener);
        addMouseMotionListener(frameDragListener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        packetListView.setCellRenderer(new ListRowView());
        packetListView.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateViews(packetListView.getSelectedValue());
            }
        });
        packetListView.setModel(packetListViewModel);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        JButton closeMenuButton = new JButton();
        closeMenuButton.addActionListener(new ExitAction());
        closeMenuButton.setIcon(new FlatSVGIcon("close.svg"));
        closeMenuButton.setToolTipText("Закрыть");
        menuBar.add(closeMenuButton);
        setJMenuBar(menuBar);
        reloadData.addActionListener(this::reloadData);
        openFileButton.addActionListener(e -> openFileDialog());
    }

    public MyForm(Parser p) {
        this();
        this.parser = p;
        updateParser(parser);
    }

    private void reloadData(ActionEvent actionEvent) {

    }

    private JMenu createFileMenu() {
        JMenuItem exit, open;
        JMenu file = new JMenu("Файл");
        open = new JMenuItem("Открыть", new FlatSVGIcon("menu-open.svg"));
        exit = new JMenuItem(new ExitAction("Выход"));
        exit.setIconTextGap(10);
        exit.setIcon(new FlatSVGIcon("close.svg"));
        file.add(open);
        file.addSeparator();
        file.add(exit);
        open.setIconTextGap(10);
        open.addActionListener(actionEvent -> openFileDialog());
        return file;
    }

    private void openFileDialog() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAccessory(new CheckBoxAccessory("Resolve domain"));
        if (parser != null) {
            chooser.setCurrentDirectory(parser.getFile());
        }
        int res = chooser.showDialog(this, "Открыть файл");
        if (res == JFileChooser.APPROVE_OPTION) {
            packetListViewModel.removeAllElements();
            if (parserTask != null) parserTask.cancel(true);
            File chosenFile = chooser.getSelectedFile();
            parser = FileParserFactory.produce(chosenFile.getPath());
            CheckBoxAccessory resolveDomainCheckBox = (CheckBoxAccessory) chooser.getAccessory();
            parser.setResolveDomainAddress(resolveDomainCheckBox.isBoxSelected());
            updateParser(parser);
        }
    }

    private void updateParser(Parser parser) {
        parserTask = new ParserTask(new ParserManager(parser));
        parserTask.execute();
    }

    private void updateViews(Packet packet) {
        destLabel.setText(packet.getDestination());
        sourceLabel.setText(packet.getSource());
        dateLabel.setText(packet.getDate().toString());
    }

    private void createUIComponents() {
        openFileButton = new JButton(new FlatSVGIcon("menu-open.svg"));
        reloadData = new JButton(new FlatSVGIcon("refresh.svg"));
        listLayeredPane = new JLayeredPane();
        loadingPanel = new LoadingPanel();
        listLayeredPane.add(loadingPanel, JLayeredPane.PALETTE_LAYER);
        loadingPanel.setBounds(320, 10, 130, 30);
    }

    static class ExitAction extends AbstractAction {
        @Serial
        private static final long serialVersionUID = 1L;

        ExitAction() {

        }

        ExitAction(String name) {
            putValue(NAME, name);
        }

        public void actionPerformed(ActionEvent e) {
            if (parserTask != null) parserTask.cancel(true);
            System.exit(0);
        }
    }

    class ParserTask extends SwingWorker<Void, Packet> {
        ParserManager manager;

        public ParserTask(ParserManager manager) {
            this.manager = manager;
            manager.setFileParsedListener(this::fileParsed);
            manager.setPacketParsedListener(this::packetParsed);
        }

        private void packetParsed(Packet packet) {
            publish(packet);
            SwingUtilities.invokeLater(() -> packetListViewModel.addElement(packet));
        }

        private void fileParsed() {
            loadingPanel.stop();
        }

        @Override
        public Void doInBackground() {
            loadingPanel.start();
            manager.startParse();
            return null;
        }
    }
}
