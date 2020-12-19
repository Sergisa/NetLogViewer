package packet;

public class FileParserFactory {
    public static Parser produce(String filepath){
        Parser result = null;

        if(filepath.toLowerCase().endsWith(".pcapng")) {
            return new PCAPFileParser(filepath);
        }else if(filepath.toLowerCase().endsWith(".txt") || filepath.toLowerCase().endsWith(".log")){
            return new TXTFileParser(filepath);
        }
        return null;
    }
}
