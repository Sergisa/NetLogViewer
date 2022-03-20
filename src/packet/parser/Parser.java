package packet.parser;

import packet.Packet;

import java.io.File;
import java.util.List;

public interface Parser {
    List<Packet> startParse();

    void setPacketParsedListener(OnPacketParsedListener packetParsedListener);

    void setFileParsedListener(OnFileParsedListener fileParsedListener);

    File getFile();

    boolean isResolveDomainAddress();

    void setResolveDomainAddress(boolean resolveDomainAddress);
}
