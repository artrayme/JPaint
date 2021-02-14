package mainwindow.shapes;

import java.awt.*;

public class Pencil extends Shape{

    public Pencil(){

    }

    public Pencil (Color color){
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawLine(startX, startY, endX, endY);
    }
}
