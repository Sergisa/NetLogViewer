import packet.FileParserFactory;
import packet.Packet;

import java.util.List;

public class Main {

    public static void main(String[] args){
        MyForm form = new MyForm();
        List<Packet> packetsList = FileParserFactory.produce("test/java/2-iptraf.log").getPackets();
        form.setPackets(packetsList);
        form.setSize(500,500);
    }
}
