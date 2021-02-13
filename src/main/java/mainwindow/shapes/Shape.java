package mainwindow.shapes;

import java.awt.*;

public abstract class Shape {
    protected int startX;
    protected int startY;
    protected int endX;
    protected int endY;

    protected Color color = Color.BLACK;

    Shape (){}

    Shape(int startX, int startY, int endX, int endY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }


    public abstract void draw(Graphics g);

}
