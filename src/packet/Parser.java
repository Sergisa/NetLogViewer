package packet;

import java.io.File;

public interface Parser {
    void run();

    void setFileParsedListener(OnFileParsedListener fileParsedListener);

    void setPacketParsedListener(OnPacketParsedListener packetParsedListener);

    File getFile();
}
