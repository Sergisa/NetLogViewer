package packet;

public class FileReaderFactory {
    public static FileReader produce(String filepath){
        FileReader result = null;

        if(filepath.toLowerCase().endsWith(".pcapng")) {
            return new PCAPFileReader(filepath);
        }else if(filepath.toLowerCase().endsWith(".txt") || filepath.toLowerCase().endsWith(".log")){
            return new TXTFileReader(filepath);
        }
        return null;
    }
}
