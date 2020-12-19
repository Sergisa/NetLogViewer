import packet.FileParserFactory;
import packet.Packet;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        MyForm form = new MyForm();
        List<Packet> packetsList = FileParserFactory.produce("packets.log").getPackets();
        form.setPackets(packetsList);
        form.setSize(500,500);
    }
}
