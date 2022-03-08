package packet;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.IpV6Packet;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PCAPFileParser extends AbstractParser implements Parser {

    public PCAPFileParser(File file) {
        this.file = file;
    }

    public PCAPFileParser(String filepath) {
        this(new File(filepath));
    }

    public List<Packet> readPackets() {
        final PcapHandle handle;
        final ArrayList<Packet> packets = new ArrayList<>();
        try {
            handle = Pcaps.openOffline(this.file.getPath());
            handle.dispatch(80, packet -> {
                try {
                    if (packet.get(IpV4Packet.class) != null) {
                        IpPacket.IpHeader v4Header = packet.get(IpV4Packet.class).getHeader();
                        Packet p = new Packet(
                                packet.getTimestamp().toString(),
                                packet.getPayload().length(),
                                v4Header.getProtocol().name(),
                                v4Header.getSrcAddr().getHostAddress(),
                                v4Header.getDstAddr().getHostAddress()
                        );
                        packets.add(p);
                        packetParsedListener.parsed(p);
                    } else if (packet.get(IpV6Packet.class) != null) {
                        IpPacket.IpHeader v6Header = packet.get(IpV6Packet.class).getHeader();
                        Packet p = new Packet(
                                packet.getTimestamp().toString(),
                                packet.getPayload().length(),
                                v6Header.getProtocol().name(),
                                v6Header.getSrcAddr().getHostAddress(),
                                v6Header.getDstAddr().getHostAddress()
                        );
                        packets.add(p);
                        packetParsedListener.parsed(p);
                    }
                } catch (ParseException ignored) {

                }
            });
            fileParsedListener.parsed(packets);
        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            e.printStackTrace();
        }
        return packets;
    }

    @Override
    public void run() {
        fileParsedListener.parsed(readPackets());
    }
}
