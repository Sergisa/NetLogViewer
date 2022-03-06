import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

class LoadingPanel extends JPanel {

    private final FlatSVGIcon loadingIcon = new FlatSVGIcon("refresh.svg");
    private int loadingCounter = 0;
    private final JLabel loadingLabel;
    private Timer loadingIconTimer;

    public LoadingPanel(LayoutManager layout) {
        super(layout);
        loadingLabel = new JLabel("Загрузка данных");
        setLayout(new BorderLayout());
        loadingLabel.setForeground(new Color(0x658C61));
        loadingLabel.setIcon(loadingIcon);
        loadingLabel.setVerticalAlignment(SwingConstants.CENTER);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loadingLabel, BorderLayout.CENTER);
        setOpaque(true);
        setBackground(null);
        InitIcon();
    }

    LoadingPanel() {
        this(new BorderLayout());
    }

    private void InitIcon() {
        loadingIcon.setColorFilter(new FlatSVGIcon.ColorFilter(color -> {
            loadingCounter += 6;
            loadingCounter %= 255;
            Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), loadingCounter);
            System.out.print(c);
            System.out.println(loadingCounter);
            return c;
        }));
        loadingLabel.setIcon(loadingIcon);

        loadingIconTimer = new Timer(30, e -> loadingLabel.repaint());
    }

    public void start() {
        loadingIconTimer.start();
    }
}