package test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import packet.TXTFileParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TXTFileParserTest {

    @Test
    public void getPackets() {
        String address = getClass().getResource( "2-iptraf.log").getPath();
        TXTFileParser parser = new TXTFileParser(address);
        Assertions.assertEquals(742, parser.getPackets().size(), 0);
    }

    @Test
    void read() {
        String address = getClass().getResource("2-iptraf.log").getPath();
        TXTFileParser parser = new TXTFileParser(address);
        Assertions.assertEquals(743, parser.read().size(), 0);
    }

    @Test
    void regex() {
        Pattern destSourcePattern = Pattern.compile("from ([^;]*) to ([^;]*)");
        Matcher m = destSourcePattern.matcher("from 192.168.103.253:62172 to 81-1-183-199.broadband.progtech.ru:57847");


        System.out.println(m.find());
        System.out.println(m.group(0));
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        Assertions.assertNotNull(
                m.group(0)
        );
        /*Assertions.assertNotNull(
            destSourcePattern.matcher("Tue Aug 21 12:29:26 2018; UDP; eth0; 139 bytes; from 192.168.103.1:45323 to 255.255.255.255:5678").group(1)
        );*/
    }
}