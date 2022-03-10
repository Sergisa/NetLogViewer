package packet.parser;

import java.io.File;

public abstract class AbstractParser {
    protected File file;
    protected boolean resolveDomainAddress = false;
    OnPacketParsedListener packetParsedListener;

    public AbstractParser(String filepath) {
        this(new File(filepath));
    }

    public AbstractParser(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        this.packetParsedListener = packetParsedListener;
    }

    public boolean isResolveDomainAddress() {
        return resolveDomainAddress;
    }

    public void setResolveDomainAddress(boolean resolveDomainAddress) {
        this.resolveDomainAddress = resolveDomainAddress;
    }
}
