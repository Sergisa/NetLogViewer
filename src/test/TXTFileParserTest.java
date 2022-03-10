package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import packet.parser.TXTFileParser;

public class TXTFileParserTest {
    String address = getClass().getResource("iptraf.log").getPath();
    final TXTFileParser parser = new TXTFileParser(address);

    @BeforeEach
    void setUp() {
    }

    @Test
    public void getPackets() {
        /*Assertions.assertNotSame(0, parser.readPackets().size());
        Assertions.assertEquals(50, parser.readPackets().size());*/
    }
}