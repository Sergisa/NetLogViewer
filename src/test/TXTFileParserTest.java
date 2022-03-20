package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import packet.Packet;
import packet.parser.AbstractParser;
import packet.parser.TXTFileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
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
        String yandexIP = "185.9.147.1";
        String yandexDNS = "shared-25.smartape.ru";
        try {
            Assertions.assertEquals(yandexDNS, Inet4Address.getByName(yandexIP).getHostName(), "IP Address to domain");
            Assertions.assertEquals(yandexDNS, Inet4Address.getByName(yandexDNS).getCanonicalHostName(), "Domain to domain");
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void sbSrTest() {
        StringReader sr = new StringReader("""
                Tue Aug 21 12:27:20 2018; ******** IP traffic monitor started ********\r
                Tue Aug 21 12:32:26 2018; UDP; eth0; 139 bytes; from 192.168.103.1:45323 to 255.255.255.255:5678\r
                Tue Aug 21 12:27:43 2018; TCP; eth0; 72 bytes; from 81-1-183-199.broadband.progtech.ru:57791 to 192.168.103.253:ftp; first packet\r
                """);
        BufferedReader br = new BufferedReader(sr);

        try {
            Assertions.assertEquals("Tue Aug 21 12:27:20 2018; ******** IP traffic monitor started ********", br.readLine());
            Assertions.assertEquals("Tue Aug 21 12:32:26 2018; UDP; eth0; 139 bytes; from 192.168.103.1:45323 to 255.255.255.255:5678", br.readLine());
            Assertions.assertEquals("Tue Aug 21 12:27:43 2018; TCP; eth0; 72 bytes; from 81-1-183-199.broadband.progtech.ru:57791 to 192.168.103.253:ftp; first packet", br.readLine());
            Assertions.assertNull(br.readLine());
            Assertions.assertNull(br.readLine());
            Assertions.assertNull(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void startParse() {
        TXTFileParser parser = new TXTFileParser();

        String packetStrings = """
                Tue Aug 21 12:27:20 2018; ******** IP traffic monitor started ********\r
                Tue Aug 21 12:32:26 2018; UDP; eth0; 139 bytes; from 192.168.103.1:45323 to 255.255.255.255:5678\r
                Tue Aug 21 12:27:43 2018; TCP; eth0; 72 bytes; from 81-1-183-199.broadband.progtech.ru:57791 to 192.168.103.253:ftp; first packet\r
                """;
        List<Packet> packetList = parser.readStrings(packetStrings);

        Assertions.assertEquals(2, packetList.size(), "Illegal row added to packet list");
    }

    @Test
    void parseStep() throws AbstractParser.FileParserException {
        TXTFileParser parser = new TXTFileParser();

        String illegalPacket = "Tue Aug 21 12:27:20 2018; ******** IP traffic monitor started ********\r\n";
        Assertions.assertThrows(AbstractParser.FileParserException.class, () -> parser.parseStep(illegalPacket), "Illegal row returns Packet");

        String illegalDatePacket = "Tue Авг 21 12:27:42 2018; TCP; eth0; 71 bytes; from 192.168.103.253:ftp to 81-1-183-199.broadband.progtech.ru:57788; first packet";
        Assertions.assertThrows(AbstractParser.FileParserException.class, () -> parser.parseStep(illegalDatePacket), "Illegal date parse doesn't thrown");

        String testPacket = "Tue Aug 21 12:27:26 2018; UDP; eth0; 139 bytes; from 192.168.103.1:45323 to 255.255.255.255:5678";
        Packet packet = Packet.Builder.aPacket()
                .withDate("Tue Aug 21 12:27:26 2018")
                .withType(Packet.Type.UDP)
                .withBytes(139)
                .withSource("192.168.103.1:45323")
                .withDestination("255.255.255.255:5678")
                .build();
        Assertions.assertEquals(packet, parser.parseStep(testPacket), "Not recognized packet data");
    }
}