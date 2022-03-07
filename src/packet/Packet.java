package packet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;

public class Packet {
    private final Date date;
    private final int bytes;
    private final Type type;
    private final String source;
    private final String destination;

    public Packet(Date date, int bytes, Type type, String source, String destination) {
        this.date = date;
        this.bytes = bytes;
        this.type = type;
        this.source = source;
        this.destination = destination;
    }

    public Packet(String date, int bytes, String type, String source, String destination) throws ParseException {
        this(
                new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH).parse(date),
                bytes,
                Type.build(type),
                source,
                destination
        );
    }

    public Packet(String s, Matcher m) throws ParseException {
        this(
                s.split("; ")[0],
                Integer.parseInt(s.split("; ")[3].replace(" bytes", "")),
                s.split("; ")[1],
                m.group(1),
                m.group(2)
        );
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "bytes=" + bytes +
                ", type=" + type +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    public enum Type {
        TCP,
        UDP,
        ICMP,
        UNKNOWN;

        static Type build(String name) {
            return switch (name) {
                case "UDP" -> UDP;
                case "TCP" -> TCP;
                case "ICMP" -> ICMP;
                default -> UNKNOWN;
            };
        }
    }
}
