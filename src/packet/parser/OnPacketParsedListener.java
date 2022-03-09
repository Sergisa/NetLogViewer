package packet.parser;

import packet.Packet;

public interface OnPacketParsedListener {
    void parsed(Packet packet);
}
