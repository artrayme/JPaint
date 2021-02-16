package mainwindow.shapes;

import java.awt.*;

public class Rectangle extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX-startX), Math.abs(endY-startY));
    }
}
