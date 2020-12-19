package packet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TXTFileParser implements Parser {
    private final File file;
    public TXTFileParser(String filepath) {
        this(new File(filepath));
    }

    public TXTFileParser(File file) {
        this.file = file;
    }

    public List<String> read() {
        return new ArrayList<String>();
    }

    public List<Packet> getPackets(){
        List<Packet> packets = new ArrayList<>();
        this.read().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                //TODO: наполнение списка объектов Packet
            }
        });
        return packets;
    }
}
