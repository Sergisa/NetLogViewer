package ui;

import javax.swing.*;
import java.awt.*;

public class CheckBoxAccessory extends JComponent {
    JCheckBox virtualCheckBox;
    boolean checkBoxInit = false;

    int preferredWidth = 150;
    int preferredHeight = 100;
    int checkBoxPosX = 5;
    int checkBoxPosY = 20;
    int checkBoxWidth = preferredWidth;
    int checkBoxHeight = 20;

    public CheckBoxAccessory(String description) {
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        virtualCheckBox = new JCheckBox(description, checkBoxInit);
        virtualCheckBox.setBounds(checkBoxPosX, checkBoxPosY, checkBoxWidth, checkBoxHeight);
        add(virtualCheckBox);
    }

    public boolean isBoxSelected() {
        return virtualCheckBox.isSelected();
    }
}
