package mainwindow.shapes;

import java.awt.*;

public class Line extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawLine(startX, startY, endX, endY);
    }
}
