package packet;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TXTFileParser extends AbstractParser implements Parser {
    private final int SOURCE_DEST_COLUMN_INDEX = 4;
    private final String COLUMN_SPLITTER = "; ";

    public TXTFileParser(String filepath) {
        this(new File(filepath));
    }

    public TXTFileParser(File file) {
        this.file = file;
    }

    public List<Packet> readPackets() {
        List<Packet> localParsedPacketList = new ArrayList<>();
        Pattern destSourcePattern = Pattern.compile("from ([^;]*) to ([^;]*)");

        readStrings().forEach(fileLine -> {
            try {
                if (!fileLine.contains("IP traffic monitor started")) {
                    Matcher matcher = destSourcePattern.matcher(fileLine.split(COLUMN_SPLITTER)[SOURCE_DEST_COLUMN_INDEX]);
                    if (matcher.find()) {
                        Packet parsedPacket = new Packet(fileLine, matcher);
                        localParsedPacketList.add(parsedPacket);
                        packetParsedListener.parsed(parsedPacket);
                    }
                }
            } catch (ParseException ignored) {

            }
        });
        return localParsedPacketList;
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

        return result;
    }

    public void run() {
        fileParsedListener.parsed(readPackets());
    }
}
