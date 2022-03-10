package packetListModel;

import packet.Packet;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class ListRowView implements ListCellRenderer<Packet> {
    private static final Color INDIGO = new Color(0x3F, 0x51, 0xB5);
    private JLabel packetTypeLabel;
    private JLabel packetSourceLabel;
    private JLabel packetDestinationLabel;
    private JPanel root;
    private JLabel dateTimeLabel;

    public ListRowView() {
        root.setOpaque(true);
        root.setVisible(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Packet> list, Packet packet, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            root.setBackground(INDIGO);
            root.setForeground(Color.white);
        } else {
            root.setBackground(UIManager.getColor("List.background"));
            root.setForeground(UIManager.getColor("List.foreground"));
        }
        if (packet.getDate() != null) {
            dateTimeLabel.setText(new SimpleDateFormat("dd MMM yyy").format(packet.getDate()));
        }
        packetTypeLabel.setText(packet.getType().toString());
        packetSourceLabel.setText(packet.getSource());
        packetDestinationLabel.setText(packet.getDestination());
        return root;
    }
}
