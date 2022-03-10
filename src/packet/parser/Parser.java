package packet.parser;

import java.io.File;

public interface Parser {
    void getPackets();

    void setPacketParsedListener(OnPacketParsedListener packetParsedListener);

    File getFile();

    boolean isResolveDomainAddress();

    void setResolveDomainAddress(boolean resolveDomainAddress);
}
