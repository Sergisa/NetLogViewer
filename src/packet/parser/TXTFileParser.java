package packet.parser;

import packet.Packet;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TXTFileParser extends AbstractParser implements Parser {

    public TXTFileParser(String filepath) {
        this(new File(filepath));
    }

    public TXTFileParser(File file) {
        this.file = file;
    }

    public void getPackets() {
        readStrings().forEach(fileLine -> {
            if (!fileLine.contains("IP traffic monitor started")) {
                Packet parsedPacket = Packet.Builder.aPacket().fromString(fileLine).build();
                if (packetParsedListener != null) {
                    packetParsedListener.parsed(parsedPacket);
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
