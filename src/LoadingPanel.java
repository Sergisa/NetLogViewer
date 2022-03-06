import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

class LoadingPanel extends JPanel {

    public LoadingPanel(LayoutManager layout) {
        super(layout);
        JLabel loadingLabel = new JLabel("Загрузка данных");
        setLayout(new BorderLayout());
        loadingLabel.setForeground(new Color(0x658C61));
        loadingLabel.setIcon(new FlatSVGIcon("refresh.svg"));
        loadingLabel.setVerticalAlignment(SwingConstants.CENTER);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loadingLabel, BorderLayout.CENTER);
        setOpaque(true);
        setBackground(new Color(0xECF0F1));
    }

    LoadingPanel() {
        this(new BorderLayout());
    }
}