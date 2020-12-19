import packet.FileParserFactory;
import packet.Packet;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        MyForm form = new MyForm();
        List<Packet> packetList = FileParserFactory.produce("packets.log").getPackets();
        form.setPackets(packetList);
        List<Packet> packetsList = new ArrayList<>();
        packetsList.add(new Packet(Packet.Type.TCP).setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        packetsList.add(new Packet(Packet.Type.UDP).setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        packetsList.add(new Packet(Packet.Type.UDP).setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        packetsList.add(new Packet(Packet.Type.TCP).setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        form.setPackets(packetsList);
        form.setSize(500,500);
    }
}
