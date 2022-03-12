import com.formdev.flatlaf.FlatDarculaLaf;
import ui.MyForm;

import javax.swing.*;

public class Main {
    private final static String file = "src/test/iptraf.log";

    public static void main(String[] args) {
        FlatDarculaLaf.install();
        SwingUtilities.invokeLater(() -> {
            MyForm form = new MyForm();
            form.setSize(500, 550);
        });
    }
}
