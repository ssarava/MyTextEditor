import java.awt.Image;

import javax.swing.ImageIcon;

public class Theme {
    
    private String theme;
    private Image bgImage;
    private int imageX, imageY, imageWidth, imageHeight;

    private Theme(String fileName) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/" + theme));
        bgImage = icon.getImage();
        
    }

    private void setDimensions(int xPos, int yPos, int widthIn, int heightIn) {
        imageX = xPos;
        imageY = yPos;
        imageWidth = widthIn;
        imageHeight = heightIn;
    }
}
