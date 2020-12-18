package pck;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageResizer {
    public static ImageIcon getResized(ImageIcon image, int w, int h){
        image.setImage( getScaledImage(image.getImage(), w, h ));
        return image;
    }

    private static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
}
