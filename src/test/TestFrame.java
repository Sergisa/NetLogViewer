package test;

import com.formdev.flatlaf.FlatDarculaLaf;
import packet.Packet;
import ui.ListRowView;

import javax.swing.*;
import java.awt.*;

public class TestFrame extends JFrame {
    final Component rootPanel;

    public TestFrame() {
        FlatDarculaLaf.install();
        Packet demoPacket;
        demoPacket = Packet.Builder
                .aPacket()
                //.fromString("Tue Aug 21 12:32:46 2018; TCP; eth0; 54 bytes; from 192.168.103.253:ftp to 81-1-183-199.broadband.progtech.ru:57788; FIN sent; 11 packets, 640 bytes, avg flow rate 0.02 kbits/s")
                .build();

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
