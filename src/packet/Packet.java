package packet;

public class Packet {

    private Type type;
    private String code;
    private String source;
    private String destination;

    public Packet(Type type, String code) {
        this.type = type;
        this.code = code;
    }

    public static Packet build(String s) {
        return new Packet(Type.TCP, "3");
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        TCP,
        UDP
    }
}
