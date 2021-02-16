package mainwindow.shapes;

import java.awt.*;

public class Background extends Shape{
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(0,0, endX, endY);
    }
}
