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
        g.drawLine(startX, startY, endX, endY);
    }
}
