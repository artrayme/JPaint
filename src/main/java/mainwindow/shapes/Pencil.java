package mainwindow.shapes;

import java.awt.*;

public class Pencil extends Shape{

    public Pencil(){

    }

    public Pencil(int startX, int startY, int endX, int endY, Color color, int thickness){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawLine(startX, startY, endX, endY);
    }
}
