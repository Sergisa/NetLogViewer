package packet;

import java.util.ArrayList;
import java.util.List;

public class PCAPFileReader extends Reader{
    @Override
    public List<Packet> getPackets() {
        return null;
    }

    public PCAPFileReader(String filepath) {
        super(filepath);
    }

    @Override
    public List<String> read() {
        return new ArrayList<>();
    }

    public String readLine(int num){
        return "";
    }
}
