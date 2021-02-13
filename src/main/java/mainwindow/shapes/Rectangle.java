package mainwindow.shapes;

import java.awt.*;

public class Rectangle extends Shape {
    public Rectangle() {
    }

    public Rectangle(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawRect(startX, startY, endX-startX, endY-startY);
    }
}
