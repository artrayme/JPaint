package mainwindow.shapes;

import java.awt.*;

public class Ellipse extends Shape {

    public Ellipse(){

    }

    public Ellipse(int startX, int startY, int endX, int endY, Color color, int thickness){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawOval(Math.min(startX, endX), Math.min(startY, endY), Math.abs(endX-startX), Math.abs(endY-startY));
    }
}
