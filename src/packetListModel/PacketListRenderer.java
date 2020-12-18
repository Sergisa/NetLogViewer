package packetListModel;

import packet.Packet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class PacketListRenderer extends JLabel implements ListCellRenderer<Packet> {
    private static final Color INDIGO = new Color(0x3F, 0x51, 0xB5);
    private static final Color DEEP_PURPLE = new Color(0x67, 0x3A, 0xB7);
    private static final Color BLUE = new Color(0x21, 0x96, 0xF3);

    private static final Color HIGHLIGHT_TEXT_COLOR = new Color(0xB3, 0xE5, 0xFC);
    ImageIcon imageIcon;
    public PacketListRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Packet> list, Packet packet, int index, boolean isSelected, boolean cellHasFocus) {
        imageIcon = new ImageIcon(getClass().getResource(packet.getType().toString() + ".png"));
        imageIcon.setImage( imageIcon.getImage().getScaledInstance(30,30,2) );

        if (isSelected) {
            setBackground(INDIGO);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.DARK_GRAY);
        }
        setText(packet.getType().toString());
        setIcon(imageIcon);
        return this;
    }
}
