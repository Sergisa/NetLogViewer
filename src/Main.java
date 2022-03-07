import com.formdev.flatlaf.FlatDarculaLaf;
import packet.FileParserFactory;

import javax.swing.*;

public class Main {
    private final static String file = "src/test/iptraf.log";

    public static void main(String[] args) {
        FlatDarculaLaf.install();
        System.setProperty("cache.lastFile", file);
        SwingUtilities.invokeLater(() -> {
            MyForm form = new MyForm(FileParserFactory.produce(file));
            form.setSize(500, 500);
        });
    }
}
