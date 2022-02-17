package packetListModel;

import packet.Packet;

import javax.swing.*;
import java.awt.*;

public class PacketListViewRenderer extends JLabel implements ListCellRenderer<Packet> {
    private static final Color INDIGO = new Color(0x3F, 0x51, 0xB5);
    public PacketListViewRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Packet> list, Packet packet, int index, boolean isSelected, boolean cellHasFocus){

        if (isSelected) {
            setBackground(INDIGO);
            setForeground(Color.white);
        } else {
            setBackground(UIManager.getColor("List.background"));
            setForeground(UIManager.getColor("List.foreground"));
        }
        setText(packet.getType().toString());
        return this;
    }
}
