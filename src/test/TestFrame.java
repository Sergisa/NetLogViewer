package test;

import com.formdev.flatlaf.FlatDarculaLaf;
import packet.Packet;
import packetListModel.ListRowView;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

public class TestFrame extends JFrame {
    final Component rootPanel;

    public TestFrame() {
        FlatDarculaLaf.install();
        Packet demoPacket = null;
        try {
            demoPacket = new Packet("Tue Aug 21 12:27:26 2018", 60, "ICMP", "192.56.3.5", "192.56.3.5");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rootPanel = new ListRowView().getListCellRendererComponent(
                new JList<>(),
                demoPacket,
                0,
                false,
                false
        );
        add(rootPanel);
        setContentPane((Container) rootPanel);
        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        new TestFrame();
    }
}
