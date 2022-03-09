package packet.parser;

public class ParserManager {

    private final Parser parser;
    OnFileParsedListener fileParsedListener;
    OnPacketParsedListener packetParsedListener;
    Thread parserThread;

    public ParserManager(Parser parser) {
        this.parser = parser;
        parserThread = new Thread(() -> {
            if (fileParsedListener != null) {
                fileParsedListener.parsed(parser.getPackets());
            }
        });
    }

    public void startParse() {

    }

    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        this.packetParsedListener = packetParsedListener;
    }

    public void setFileParsedListener(OnFileParsedListener fileParsedListener) {
        this.fileParsedListener = fileParsedListener;
    }


}
