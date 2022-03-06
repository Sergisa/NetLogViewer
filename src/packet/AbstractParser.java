package packet;

public abstract class AbstractParser {
    OnFileParsedListener fileParsedListener = packets -> {

    };
    OnPacketParsedListener packetParsedListener = packet -> {

    };

    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        this.packetParsedListener = packetParsedListener;
    }

    public void setFileParsedListener(OnFileParsedListener fileParsedListener) {
        this.fileParsedListener = fileParsedListener;
    }
}
