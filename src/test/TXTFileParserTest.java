package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import packet.Packet;
import packet.parser.AbstractParser;
import packet.parser.TXTFileParser;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.List;

public class TXTFileParserTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void inetAddressConstruction() {
        String IP = "185.9.147.1";
        String DNS = "shared-25.smartape.ru";

        Assertions.assertDoesNotThrow(() -> {
            new InetSocketAddress("123.0.0.1", 80);
            new InetSocketAddress("yandex.ru", 80);
        }, "throws");
        Assertions.assertEquals(DNS, InetSocketAddress.createUnresolved(IP, 80).getHostName(), "IP is not converted to DNS");
        Assertions.assertEquals(DNS, new InetSocketAddress(IP, 80).getHostName(), "IP is not converted to DNS");
        Assertions.assertEquals(DNS, new InetSocketAddress(DNS, 80).getHostName(), "DNS is not converted to DNS");
        Assertions.assertEquals("/" + IP, new InetSocketAddress(IP, 80).getAddress().toString(), "IP is not converted to IP");
    }

    @Test
    void parseSomeLines() {
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
    void resolveDNSCheck() throws AbstractParser.FileParserException {
        TXTFileParser parser = new TXTFileParser();
        String IPsmrtp = "185.9.147.1:80";
        String DNSsmrtp = "shared-25.smartape.ru:80";
        String IP2 = "192.168.103.1";
        String IP3 = "255.255.255.255";
        String DNS1 = "81-1-183-199.broadband.progtech.ru";

        try {
            Assertions.assertEquals(DNSsmrtp, parser.parseAddress(IPsmrtp, true), "resolve IP as DNS");
            Assertions.assertEquals(IPsmrtp, parser.parseAddress(IPsmrtp, false), "resolve IP as IP");
        } catch (UnknownHostException e) {
            Assertions.fail("adress resolve failed with exception");
            e.printStackTrace();
        }
    }

    @Test
    void parseStep() throws AbstractParser.FileParserException {
        TXTFileParser parser = new TXTFileParser();

        String illegalPacket = "Tue Aug 21 12:27:20 2018; ******** IP traffic monitor started ********\r\n";
        Assertions.assertThrows(AbstractParser.FileParserException.class, () -> parser.parseStep(illegalPacket), "Illegal row returns Packet");

        String illegalDatePacket = "Tue Авг 21 12:27:42 2018; TCP; eth0; 71 bytes; from 192.168.103.253:ftp to 81-1-183-199.broadband.progtech.ru:57788; first packet";
        Assertions.assertThrows(AbstractParser.FileParserException.class, () -> parser.parseStep(illegalDatePacket), "Illegal date parse doesn't thrown");

        Packet IPtoIP_Packet = Packet.Builder.aPacket()
                .withDate("Tue Aug 21 12:27:26 2018")
                .withType(Packet.Type.UDP)
                .withBytes(139)
                .withSource("192.168.103.1")
                .withDestination("255.255.255.255")
                .build();
        Packet DNStoDNS_Packet = Packet.Builder.aPacket()
                .withDate("Tue Aug 21 12:27:43 2018")
                .withType(Packet.Type.TCP)
                .withBytes(72)
                .withSource("81-1-183-199.broadband.progtech.ru")
                .withDestination("www.yandex.ru")
                .build();
        Packet IPtoDNS_Packet = Packet.Builder.aPacket()
                .withDate("Tue Aug 21 12:27:43 2018")
                .withType(Packet.Type.TCP)
                .withBytes(72)
                .withSource("81-1-183-199.broadband.progtech.ru")
                .withDestination("192.168.103.253")
                .build();
        String IPtoIP_PacketString = "Tue Aug 21 12:27:26 2018; UDP; eth0; 139 bytes; from 192.168.103.1:45323 to 255.255.255.255:5678";
        String DNStoIP_PacketString = "Tue Aug 21 12:27:43 2018; TCP; eth0; 72 bytes; from 81-1-183-199.broadband.progtech.ru:57791 to 192.168.103.253:ftp; first packet";
        String DNStoDNS_PacketString = "Tue Aug 21 12:27:43 2018; TCP; eth0; 72 bytes; from 81-1-183-199.broadband.progtech.ru:57791 to www.yandex.ru:ftp; first packet";
        Assertions.assertEquals(IPtoIP_Packet, parser.parseStep(IPtoIP_PacketString), "First parsed packet with IP data mismatched");
        Assertions.assertEquals(IPtoDNS_Packet, parser.parseStep(DNStoIP_PacketString), "Second parsed packet with DNS and IP data mismatch");
        Assertions.assertEquals(DNStoDNS_Packet, parser.parseStep(DNStoDNS_PacketString), "Third parsed packet with DNS and DNS data mismatch");
    }
}