package packet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TXTFileReader extends Reader {
    public TXTFileReader(String filepath) {
        super(filepath);
    }

    @Override
    public List<String> read() {
        return new ArrayList<String>();
    }

    public String readLine(int num){
        return "";
    }

    public List<Packet> getPackets(){
        List<Packet> packets = new ArrayList<>();
        this.read().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                packets.add(Packet.build(s));
            }
        });
        return packets;
    }
}
