package packet.parser;

import java.io.File;
import java.io.Serial;

public abstract class AbstractParser {
    protected File file;
    protected boolean resolveDomainAddress = false;
    OnPacketParsedListener packetParsedListener;
    OnFileParsedListener fileParsedListener;

    public AbstractParser(String filepath) {
        this(new File(filepath));
    }

    public AbstractParser(File file) {
        this.file = file;
    }

    public AbstractParser() {

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

    public void setFileParsedListener(OnFileParsedListener fileParsedListener) {
        this.fileParsedListener = fileParsedListener;
    }

    public enum ParserStatus {
        UNKNOWN, INIT, NA, PARTIAL, COMPLETE, EOF, ERROR
    }

    public static class FileParserException extends Exception {
        @Serial
        private static final long serialVersionUID = 1L;

        public FileParserException(String msg) {
            super(msg);
        }

        public FileParserException(String msg, Exception e) {
            super(msg, e);
        }

    }
}
