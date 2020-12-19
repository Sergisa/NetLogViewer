package packet;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PCAPFileParser implements Parser{
    private final File file;
    public PCAPFileParser(File file) {
        this.file = file;
    }

    public PCAPFileParser(String filepath) {
        this(new File(filepath));
    }

    @Override
    public List<Packet> getPackets() {
        final PcapHandle handle;
        final ArrayList<Packet> packets = new ArrayList<>();
        try {
            handle = Pcaps.openOffline(this.file.getPath());
            //handle.setFilter("udp", BpfProgram.BpfCompileMode.OPTIMIZE);
            handle.dispatch(80, new PacketListener() {
                @Override
                public void gotPacket(PcapPacket packet) {
                    //TODO: наполнение списка объектов Packet
                    IpV4Packet.IpV4Header header = packet.get(IpV4Packet.class).getHeader();
                    packets.add(
                            new Packet(
                                    packet.getPayload().length(),
                                    header.getProtocol().name(),
                                    header.getSrcAddr().getHostAddress(),
                                    header.getDstAddr().getHostAddress()
                            )
                    );
                }
            });
        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            e.printStackTrace();
        }
        return packets;
    }
}
