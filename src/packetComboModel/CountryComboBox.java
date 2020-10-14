package packetComboModel;

import javax.swing.*;

public class CountryComboBox extends JComboBox {
    private DefaultComboBoxModel model;

    public CountryComboBox() {
        model = new DefaultComboBoxModel();
        setModel(model);
        setRenderer(new CountryItemRenderer());
        setEditor(new ComboElement());
    }

    /**
     * Add an array items to this combo box.
     * Each item is an array of two String elements:
     * - first element is country name.
     * - second element is path of an image file for country flag.
     * @param items
     */
    public void addItems(String[][] items) {
        for (String[] anItem : items) {
            model.addElement(anItem);
        }
    }
}
