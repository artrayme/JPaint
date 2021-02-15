package mainwindow.shapes;

import java.awt.*;

public class SelectionRectangle extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        ((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{3, 6}, 0));
        g.drawRect(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX - startX), Math.abs(endY - startY));
    }
}
