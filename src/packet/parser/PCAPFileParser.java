package packet.parser;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.IpV6Packet;
import packet.Packet;

import java.io.File;

public class PCAPFileParser extends AbstractParser implements Parser {

    public PCAPFileParser(File file) {
        this.file = file;
    }

    public PCAPFileParser(String filepath) {
        this(new File(filepath));
    }

    public void getPackets() {
        final PcapHandle handle;
        try {
            handle = Pcaps.openOffline(this.file.getPath());
            handle.dispatch(80, packet -> {
                Packet.Builder builder = Packet.Builder.aPacket()
                        .withDate(packet.getTimestamp().toString())
                        .withBytes(packet.getPayload().length());
                if (packet.get(IpV4Packet.class) != null) {
                    IpPacket.IpHeader v4Header = packet.get(IpV4Packet.class).getHeader();
                    builder.withType(v4Header.getProtocol().name())
                            .withSource(v4Header.getSrcAddr().getCanonicalHostName())
                            .withDestination(v4Header.getDstAddr().getCanonicalHostName());
                } else if (packet.get(IpV6Packet.class) != null) {
                    IpPacket.IpHeader v6Header = packet.get(IpV6Packet.class).getHeader();
                    builder.withType(v6Header.getProtocol().name())
                            .withSource(v6Header.getSrcAddr().getCanonicalHostName())
                            .withDestination(v6Header.getDstAddr().getCanonicalHostName());
                }
                if (packetParsedListener != null) {
                    packetParsedListener.parsed(builder.build());
                }
            });
        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
