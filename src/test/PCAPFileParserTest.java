package test;

import org.junit.jupiter.api.Test;
import packet.PCAPFileParser;

public class PCAPFileParserTest {

    @Test
    public void getPackets() {
        String address = getClass().getResource("log_ws.pcap").getPath();
        PCAPFileParser parser = new PCAPFileParser(address);
        parser.start();
    }
}