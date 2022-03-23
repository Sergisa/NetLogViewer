package packet;

import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Packet {
    private final Date date;
    private final int bytes;
    private final Type type;
    private final InetSocketAddress source;
    private final InetSocketAddress destination;

    protected Packet(Date date, int bytes, Type type, InetSocketAddress source, InetSocketAddress destination) {
        this.date = date;
        this.bytes = bytes;
        this.type = type;
        this.source = source;
        this.destination = destination;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public InetSocketAddress getDestination() {
        return destination;
    }

    public Type getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "date=" + date +
                ", bytes=" + bytes +
                ", type=" + type +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Packet packet = (Packet) o;

        if (bytes != packet.bytes) return false;
        if (!date.equals(packet.date)) return false;
        if (type != packet.type) return false;
        if (!source.equals(packet.source)) return false;
        return destination.equals(packet.destination);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + bytes;
        result = 31 * result + type.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }

    public enum Type {
        ICMP,
        TCP,
        UDP,
        UNKNOWN
    }

    public static final class Builder {
        private Date date;
        private int bytes;
        private Type type;
        private InetSocketAddress source;
        private InetSocketAddress destination;

        public static Builder aPacket() {
            return new Builder();
        }

        public Builder withDate(String date, String pattern) {
            try {
                return withDate(new SimpleDateFormat(pattern, Locale.ENGLISH).parse(date));
            } catch (ParseException ignored) {
                System.err.println("Unable to parse " + date);
                return this;
            }
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder withDate(String date) {
            return withDate(date, "EEE MMM d HH:mm:ss yyyy");
        }

        public Builder withBytes(String bytes) {
            return withBytes(Integer.parseInt(bytes));
        }

        public Builder withBytes(int bytes) {
            this.bytes = bytes;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withType(String type) {
            return withType(Type.valueOf(type));
        }

        public Builder withSource(InetSocketAddress source) {
            this.source = source;
            return this;
        }

        public Builder withSource(String source, int port) {
            this.source = new InetSocketAddress(source, port);
            return this;
        }

        public Builder withSource(String source) {
            return withSource(source, 0);
        }

        public Builder withDestination(InetSocketAddress destination) {
            this.destination = destination;
            return this;
        }

        public Builder withDestination(String destination, int port) {
            this.destination = new InetSocketAddress(destination, port);
            return this;
        }

        public Builder withDestination(String destination) {
            return withDestination(destination, 0);
        }

        public Builder but() {
            return aPacket()
                    .withDate(date)
                    .withBytes(bytes)
                    .withType(type)
                    .withSource(source)
                    .withDestination(destination);
        }

        public Packet build() {
            return new Packet(date, bytes, type, source, destination);
        }
    }
}
