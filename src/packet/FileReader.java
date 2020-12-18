package packet;

import java.util.List;

public interface FileReader {
    List<String> read();
    List<Packet> getPackets();
}
