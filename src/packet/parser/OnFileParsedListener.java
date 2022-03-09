package packet.parser;

import packet.Packet;

import java.util.List;

public interface OnFileParsedListener {
    void parsed(List<Packet> packets);
}
