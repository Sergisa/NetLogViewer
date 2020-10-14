import packetComboModel.ComboElement;
import packetComboModel.CountryItemRenderer;

import javax.swing.*;

public class MyForm extends JFrame{
    private JLabel label;
    private JPanel panel;
    private JComboBox<String[]> comboBox1;
    private JList<Packet> list1;
    private DefaultComboBoxModel<String[]> model;

    public MyForm(){
        model = new DefaultComboBoxModel<>();
        comboBox1.setModel(model);
        comboBox1.setRenderer(new CountryItemRenderer());
        comboBox1.setEditor(new ComboElement());


        setContentPane(panel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
    }

    public MyForm updateCompnents(String[][] list){
        for (String[] anItem : list) {
            model.addElement(anItem);
        }
        return this;
    }
}
