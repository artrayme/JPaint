package mainwindow.shapes;

import java.awt.*;

public class Line extends Shape {
    public Line() {
    }

    public Line(int startX, int startY, int endX, int endY, Color color) {
        super(startX, startY, endX, endY, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawLine(startX, startY, endX-startX, endY-startY);
    }
}
