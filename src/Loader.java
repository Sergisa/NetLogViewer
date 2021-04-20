import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * Created by JFormDesigner on Sun Dec 20 13:12:02 MSK 2020
 */


/**
 * @author unknown
 */
public class Loader extends JFrame {
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JLayeredPane layeredPane3;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList list1;

    public Loader() {
        initComponents();
        setJMenuBar(menuBar1);
    }

    public void disapear() {
        final Timer timer = new Timer(100, null);

        final int steps = 25;

        timer.addActionListener(new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
                if (count <= steps) {
                    float intensity = count / (float) steps;
                    label1.setForeground(new Color(intensity, intensity, intensity));
                    count++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
        //label1.setVisible(false);
    }

    private void button1ActionPerformed(ActionEvent e) {
        disapear();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        layeredPane3 = new JLayeredPane();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();

        //======== menuBar1 ========
        {
            menuBar1.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));

            //======== menu1 ========
            {
                menu1.setText("\u0424\u0430\u0439\u043b");

                //---- menuItem1 ----
                menuItem1.setText("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u0444\u0430\u0439\u043b");
                menuItem1.setFont(new Font("Montserrat Medium", Font.PLAIN, 12));
                menuItem1.setIcon(UIManager.getIcon("Tree.openIcon"));
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("\u0417\u0430\u043a\u0440\u044b\u0442\u044c");
                menuItem2.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));
                menu1.add(menuItem2);
            }
            menuBar1.add(menu1);
        }

        //======== this ========
        var contentPane = getContentPane();

        //======== layeredPane3 ========
        {

            //---- label1 ----
            label1.setText("\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430 \u0434\u0430\u043d\u043d\u044b\u0445");
            label1.setFont(new Font("Montserrat Medium", Font.PLAIN, 14));
            label1.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setBackground(new Color(197, 202, 233, 37));
            label1.setOpaque(true);
            label1.setForeground(new Color(63, 81, 181));
            label1.setBorder(new CompoundBorder(
                    new LineBorder(new Color(63, 81, 181, 64), 1, true),
                    null));
            label1.setAlignmentY(2.5F);
            label1.setAlignmentX(2.0F);
            label1.setIconTextGap(2);
            layeredPane3.add(label1, JLayeredPane.DEFAULT_LAYER);
            label1.setBounds(new Rectangle(new Point(90, 10), label1.getPreferredSize()));

            //======== scrollPane1 ========
            {

                //---- list1 ----
                list1.setBackground(Color.white);
                scrollPane1.setViewportView(list1);
            }
            layeredPane3.add(scrollPane1, JLayeredPane.DEFAULT_LAYER);
            scrollPane1.setBounds(10, 5, 235, 205);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(layeredPane3, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(layeredPane3, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
