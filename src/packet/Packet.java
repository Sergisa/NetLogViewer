package packet;

public class Packet {
    private int bytes;
    private Type type;
    private String source;
    private String destination;

    public Packet(int bytes, Type type, String source, String destination) {
        this.bytes = bytes;
        this.type = type;
        this.source = source;
        this.destination = destination;
    }
    public Packet(int bytes, String type, String source, String destination) {
        this(bytes, Type.build(type), source, destination);
    }


    public String getSource() {
        return source;
    }

    public Packet setSource(String source) {
        this.source = source;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Packet setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        TCP,
        UDP,
        UNKNOWN;

        static Type build(String name){
            return switch (name) {
                case "UDP" -> Type.UDP;
                case "TCP" -> Type.TCP;
                default -> Type.UNKNOWN;
            };
        }
    }
}
