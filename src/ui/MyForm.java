package ui;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import packet.Packet;
import packet.parser.FileParserFactory;
import packet.parser.Parser;
import packet.parser.ParserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
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
        setJMenuBar(menuBar);
        openFileButton.addActionListener(e -> openFileDialog());
    }

    public MyForm(Parser p) {
        this();
        this.parser = p;
        updateParser(parser);
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
            parserTask.cancel(true);/* FIXME: Cannot invoke "ui.MyForm$ParserTask.cancel(boolean)" because "ui.MyForm.parserTask" is null when closing */
            File chosenFile = chooser.getSelectedFile();
            parser = FileParserFactory.produce(chosenFile.getPath());
            CheckBoxAccessory resolveDomainCheckBox = (CheckBoxAccessory) chooser.getAccessory();
            parser.setResolveDomainAddress(resolveDomainCheckBox.isBoxSelected());
            updateParser(parser);
        }
    }

    private void updateParser(Parser parser) {
        parserTask = new ParserTask(new ParserManager(parser));
        parserTask.addPropertyChangeListener(this::propertyChange);
        parserTask.execute();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + "  propertyChange");
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
            putValue(NAME, "Выход");
        }

        public void actionPerformed(ActionEvent e) {
            parserTask.cancel(true); /* FIXME: Cannot invoke "ui.MyForm$ParserTask.cancel(boolean)" because "ui.MyForm.parserTask" is null when closing */
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
