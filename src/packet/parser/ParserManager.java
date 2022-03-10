package packet.parser;

public class ParserManager {

    OnFileParsedListener fileParsedListener;
    Parser parser;

    public ParserManager(Parser p) {
        parser = p;
    }

    public void startParse() {
        parser.getPackets();
        fileParsedListener.parsed();
    }

    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        parser.setPacketParsedListener(packetParsedListener);
    }

    public void setFileParsedListener(OnFileParsedListener fileParsedListener) {
        this.fileParsedListener = fileParsedListener;
    }

}
