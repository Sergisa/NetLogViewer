package packet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Packet {
    private final Date date;
    private final int bytes;
    private final Type type;
    private final String source;
    private final String destination;

    protected Packet(Date date, int bytes, Type type, String source, String destination) {
        this.date = date;
        this.bytes = bytes;
        this.type = type;
        this.source = source;
        this.destination = destination;
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

    public Date getDate() {
        return date;
    }

    public int getBytes() {
        return bytes;
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

    public enum Type {
        ICMP,
        TCP,
        UDP,
        UNKNOWN
    }

    public static final class Builder {
        private final Pattern destSourcePattern = Pattern.compile("from ([^;]*) to ([^;]*)");
        private Date date;
        private int bytes;
        private Type type;
        private String source;
        private String destination;

        public static Builder aPacket() {
            return new Builder();
        }

        public Builder fromString(String packetString) {
            final String COLUMN_SPLITTER = "; ";
            final int DATE_COLUMN_INDEX = 0;
            final int TYPE_COLUMN_INDEX = 1;
            final int BYTES_COLUMN_INDEX = 3;
            final int SOURCE_DEST_COLUMN_INDEX = 4;
            String[] packetColumns = packetString.split(COLUMN_SPLITTER);
            Matcher matcher = destSourcePattern.matcher(packetString.split(COLUMN_SPLITTER)[SOURCE_DEST_COLUMN_INDEX]);
            if (matcher.find()) {
                try {
                    date = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH).parse(packetColumns[DATE_COLUMN_INDEX]);
                } catch (ParseException ignored) {
                    System.err.println("Unable to parse" + packetColumns[DATE_COLUMN_INDEX]);
                }
                type = Type.valueOf(packetColumns[TYPE_COLUMN_INDEX]);
                bytes = Integer.parseInt(packetColumns[BYTES_COLUMN_INDEX].replace(" bytes", ""));
                source = matcher.group(1);
                destination = matcher.group(2);
            }
            return this;
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

        public Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withDestination(String destination) {
            this.destination = destination;
            return this;
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
