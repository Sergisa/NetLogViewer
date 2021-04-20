import packet.FileParserFactory;
import packet.Packet;

import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Loader l = new Loader();
                l.setVisible(true);
                MyForm form = new MyForm();
                List<Packet> packetsList = FileParserFactory.produce("test/java/2-iptraf.log").getPackets();
                form.setPackets(packetsList);
                form.setSize(500, 500);
            }
        });

    }
}
