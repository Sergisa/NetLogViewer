package packet.parser;

import java.io.File;

public abstract class AbstractParser {
    protected File file = null;
    OnPacketParsedListener packetParsedListener;

    public File getFile() {
        return file;
    }

    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        this.packetParsedListener = packetParsedListener;
    }
}
