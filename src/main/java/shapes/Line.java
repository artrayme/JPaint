package shapes;

import java.awt.*;

public class Line extends Shape {

    public Line(){

    }

    public Line(int startX, int startY, int endX, int endY, Color color, int thickness){
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
        g.drawLine(startX, startY, endX, endY);
    }
}
