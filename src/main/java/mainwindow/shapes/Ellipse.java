package mainwindow.shapes;

import java.awt.*;

public class Ellipse extends Shape {
    public Ellipse() {
    }

    public Ellipse(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawOval(startX, startY,endX-startX, endY-startY);
    }
}
