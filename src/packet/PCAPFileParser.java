package packet;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.IpV6Packet;

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
            handle.dispatch(1400, new PacketListener() {
                @Override
                public void gotPacket(PcapPacket packet) {
                    //TODO: наполнение списка объектов Packet
                    IpV4Packet.IpV4Header v4Header = packet.get(IpV4Packet.class).getHeader();
                    packets.add(
                            new Packet(
                                    packet.getPayload().length(),
                                    v4Header.getProtocol().name(),
                                    v4Header.getSrcAddr().getHostAddress(),
                                    v4Header.getDstAddr().getHostAddress()
                            )
                    );
                    IpV6Packet.IpV6Header v6Header = packet.get(IpV6Packet.class).getHeader();
                    packets.add(
                            new Packet(
                                    packet.getPayload().length(),
                                    v6Header.getProtocol().name(),
                                    v6Header.getSrcAddr().getHostAddress(),
                                    v6Header.getDstAddr().getHostAddress()
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
