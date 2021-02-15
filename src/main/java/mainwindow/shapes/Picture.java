package mainwindow.shapes;

import javax.swing.*;
import java.awt.*;

public class Picture extends Shape{
    private final Image image;

    public Picture(Image image){
        this.image = image;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, startX, startY, null);
    }
}
