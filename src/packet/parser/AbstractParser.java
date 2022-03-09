package packet.parser;

import java.io.File;

public abstract class AbstractParser {
    protected File file = null;

    public File getFile() {
        return file;
    }
}
