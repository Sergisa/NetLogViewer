import com.formdev.flatlaf.FlatDarculaLaf;
import packet.FileParserFactory;
import packet.Packet;

import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FlatDarculaLaf.install();
        SwingUtilities.invokeLater(() -> {
            MyForm form = new MyForm();
            List<Packet> packetsList = FileParserFactory.produce("src/test/iptraf.log").getPackets();
            form.setPackets(packetsList);
            form.setSize(500, 500);
        });
    }
}
