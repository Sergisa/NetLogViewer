package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import packet.parser.TXTFileParser;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Objects;

public class TXTFileParserTest {
    String address;
    TXTFileParser parser;

    @BeforeEach
    void setUp() {
        address = Objects.requireNonNull(getClass().getResource("iptraf.log")).getPath();
        parser = new TXTFileParser(address);
    }

    @Test
    public void getPackets() {
        try {
            System.out.println(Inet4Address.getByName("77.88.55.70").getCanonicalHostName());
            System.out.println(Inet4Address.getByName("yandex.ru").getCanonicalHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}