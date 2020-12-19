package test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import packet.TXTFileParser;

public class TXTFileParserTest {

    @Test
    public void getPackets() {
        String address = getClass().getResource( "2-iptraf.log").getPath();
        TXTFileParser parser = new TXTFileParser(address);
        Assertions.assertEquals(743, parser.getPackets().size(), 0);
    }

    @Test
    void read() {
        String address = getClass().getResource( "2-iptraf.log").getPath();
        TXTFileParser parser = new TXTFileParser(address);
        Assertions.assertEquals(743, parser.read().size(), 0);
    }
}