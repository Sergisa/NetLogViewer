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
import java.util.List;

public class PCAPFileParser extends AbstractParser implements Parser {
    public PCAPFileParser(String filepath) {
        super(filepath);
    }

    public PCAPFileParser(File file) {
        super(file);
    }

    public List<Packet> startParse() {
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
                            .withSource(resolveDomainAddress ?
                                    v4Header.getSrcAddr().getCanonicalHostName() :
                                    v4Header.getSrcAddr().toString())
                            .withDestination(resolveDomainAddress ?
                                    v4Header.getDstAddr().getCanonicalHostName() :
                                    v4Header.getDstAddr().toString());
                } else if (packet.get(IpV6Packet.class) != null) {
                    IpPacket.IpHeader v6Header = packet.get(IpV6Packet.class).getHeader();
                    builder.withType(v6Header.getProtocol().name())
                            .withSource(resolveDomainAddress ?
                                    v6Header.getSrcAddr().getCanonicalHostName() :
                                    v6Header.getSrcAddr().toString())
                            .withDestination(resolveDomainAddress ?
                                    v6Header.getDstAddr().getCanonicalHostName() :
                                    v6Header.getDstAddr().toString());
                }
                if (packetParsedListener != null) {
                    packetParsedListener.parsed(builder.build());
                }
            });
        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
