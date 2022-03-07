package packet;

import java.io.File;

public abstract class AbstractParser extends Thread {
    protected File file = null;
    OnFileParsedListener fileParsedListener = packets -> System.err.println("defaultFileParsedListener");
    OnPacketParsedListener packetParsedListener = packet -> System.err.println("defaultPacketParsedListener");

    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        this.packetParsedListener = packetParsedListener;
    }

    public void setFileParsedListener(OnFileParsedListener fileParsedListener) {
        this.fileParsedListener = fileParsedListener;
    }

    public File getFile() {
        return file;
    }
}
