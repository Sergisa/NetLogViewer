package packet.parser;

import packet.Packet;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TXTFileParser extends AbstractParser implements Parser {
    public TXTFileParser(String filepath) {
        super(filepath);
    }

    public TXTFileParser(File file) {
        super(file);
    }

    public TXTFileParser() {
        super();
    }

    public List<Packet> startParse() {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(this.file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readStrings(new BufferedReader(inputStreamReader));
    }

    public List<Packet> readStrings(String strings) {
        StringReader sr = new StringReader(strings);
        BufferedReader br = new BufferedReader(sr);
        return readStrings(br);
    }

    public List<Packet> readStrings(BufferedReader reader) {
        List<Packet> result = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                try {
                    result.add(parseStep(line));
                } catch (FileParserException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (fileParsedListener != null) fileParsedListener.parsed();
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return Collections.unmodifiableList(result);
    }

    public Packet parseStep(String line) throws FileParserException {
        Packet.Builder parsedPacketBuilder = Packet.Builder.aPacket();
        final String COLUMN_SPLITTER = "; ";
        final int DATE_COLUMN_INDEX = 0;
        final int TYPE_COLUMN_INDEX = 1;
        final int BYTES_COLUMN_INDEX = 3;
        final int SOURCE_DEST_COLUMN_INDEX = 4;
        final Pattern destSourcePattern = Pattern.compile("from ([^;]*) to ([^;]*)");

        try {
            Matcher destSourceMatcher = destSourcePattern.matcher(line.split(COLUMN_SPLITTER)[SOURCE_DEST_COLUMN_INDEX]);
            String[] packetColumns = line.split(COLUMN_SPLITTER);

            if (destSourceMatcher.find()) {
                try {
                    parsedPacketBuilder.withDate(
                            new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH)
                                    .parse(packetColumns[DATE_COLUMN_INDEX])
                    );
                } catch (ParseException ignored) {
                    throw new FileParserException("Unable to parse date: " + packetColumns[DATE_COLUMN_INDEX]);
                }
                parsedPacketBuilder.withType(packetColumns[TYPE_COLUMN_INDEX])
                        .withBytes(packetColumns[BYTES_COLUMN_INDEX].replace(" bytes", ""))
                        .withSource(resolveAddress(destSourceMatcher.group(1)))
                        .withDestination(resolveAddress(destSourceMatcher.group(2)));
            }
            if (packetParsedListener != null) {
                packetParsedListener.parsed(parsedPacketBuilder.build());
            }
        } catch (NullPointerException e) {
            throw new FileParserException("Line is Empty");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new FileParserException("Cannot parse line: " + line);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return parsedPacketBuilder.build();
    }

    public InetAddress resolveAddress(String address) throws UnknownHostException {
        final Pattern IPv4Pattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        final Pattern IPv4SubPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        final Pattern IPv6Pattern = Pattern.compile("(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))");
        Matcher IPv6Matcher = IPv6Pattern.matcher(address);
        Matcher IPv4Matcher = IPv4Pattern.matcher(address);
        Matcher IPv4SubMatcher = IPv4SubPattern.matcher(address);
        if (IPv4SubMatcher.find()) {
            return InetAddress.getByName(address.replaceAll(":[\\d\\w]+", ""));
        } else if (IPv6Matcher.find()) {
            return InetAddress.getByName(address);
        } else {
            return null;
        }
    }
}
