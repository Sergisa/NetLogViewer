package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import packet.TXTFileParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TXTFileParserTest {

    @Test
    public void getPackets() {
        String address = getClass().getResource("iptraf.log").getPath();
        TXTFileParser parser = new TXTFileParser(address);
        Assertions.assertNotSame(0, parser.getPackets().size());
    }

    @Test
    void read() {
        String address = getClass().getResource("iptraf.log").getPath();
        TXTFileParser parser = new TXTFileParser(address);
        Assertions.assertNotSame(0, parser.read().size());
    }

    @Test
    void regex() {
        Pattern destSourcePattern = Pattern.compile("from ([^;]*) to ([^;]*)");
        Matcher m = destSourcePattern.matcher("from 192.168.103.253:62172 to 81-1-183-199.broadband.progtech.ru:57847");

        Assertions.assertNotNull(
                m.group(0)
        );

    }
}