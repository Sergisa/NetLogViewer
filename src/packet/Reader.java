package packet;

public abstract class Reader implements FileReader{
    String filepath="";
    public Reader(String filepath){
        this.filepath = filepath;
    }
}
