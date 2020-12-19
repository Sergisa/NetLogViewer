package packet;

public class Packet {

    private Type type;
    private String source;
    private String destination;

    public Packet(Type type) {
        this.type = type;
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
        UDP
    }
}
