package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import packet.AbstractParser;
import packet.TXTFileParser;

public class TXTFileParserTest {
    AbstractParser parser;
    String address = getClass().getResource("iptraf.log").getPath();

    @BeforeEach
    void setUp() {
        parser = new TXTFileParser(address);
    }

    @Test
    public void getPackets() {
        parser.setFileParsedListener(packets -> {
            Assertions.assertNotSame(0, packets.size());
            Assertions.assertEquals(540, packets.size());
        });
        parser.setPacketParsedListener(packets -> {
        });
        parser.start();
    }
}