package packet.parser;

import packet.Packet;

import java.io.File;
import java.util.List;

public interface Parser {
    List<Packet> getPackets();

    File getFile();
}
