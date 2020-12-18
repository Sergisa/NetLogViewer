import packet.FileReaderFactory;
import packet.Packet;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        MyForm form = new MyForm();
        List<Packet> packetList = FileReaderFactory.produce("packets.log").getPackets();

        List<Packet> packetsList = new ArrayList<>();
        packetsList.add(new Packet(Packet.Type.TCP, "csdf").setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        packetsList.add(new Packet(Packet.Type.UDP, "ajshgblh").setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        packetsList.add(new Packet(Packet.Type.UDP, "werwe").setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        packetsList.add(new Packet(Packet.Type.TCP, "ajshwetdfghgblh").setSource("145.45.78.2:80").setDestination("145.45.78.2:7864"));
        form.setPackets(packetsList);
        form.setSize(500,500);
    }
}
