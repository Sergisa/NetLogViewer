package packet;

import java.util.List;

public interface OnFileParsedListener {
    void parsed(List<Packet> packets);
}
