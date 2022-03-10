package packet.parser;

import packet.Packet;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TXTFileParser extends AbstractParser implements Parser {
    public TXTFileParser(String filepath) {
        super(filepath);
    }

    public TXTFileParser(File file) {
        super(file);
    }

    public void getPackets() {
        readStrings().forEach(fileLine -> {
            if (!fileLine.contains("IP traffic monitor started")) {
                Packet.Builder parsedPacketBuilder = Packet.Builder.aPacket().fromString(fileLine);
                if (packetParsedListener != null) {
                    packetParsedListener.parsed(parsedPacketBuilder.build());
                }
            }
        });
    }

    private List<String> readStrings() {
        List<String> result = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.ready()) {
                result.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.unmodifiableList(result);
    }
}
