package mainwindow;

import mainwindow.shapes.Rectangle;
import mainwindow.shapes.Shape;
import mainwindow.shapes.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class DrawArea extends JPanel {
    private MouseInputAdapter currentEvent = new PencilMouseEvent();
    private final LinkedList<Shape> shapes = new LinkedList<>();
    private Shape currentShape;
    private Color currentColor = Color.BLACK;
    private int thickness = 1;

    public DrawArea() {
        setBackground(Color.white);
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        if (currentShape != null)
            currentShape.draw(g);
    }

    public void pencil() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new PencilMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    public void rectangle() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new RectangleMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    public void line() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new LineMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    public void ellipse() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new EllipseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    public void erase() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new EraseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    public void selection() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new LineMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    public void lasso() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new LineMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
    }

    class PencilMouseEvent extends MouseInputAdapter {
        private int X2;
        private int Y2;

        @Override
        public void mousePressed(MouseEvent e) {
            X2 = e.getX();
            Y2 = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape = new Pencil();
            currentShape.setColor(currentColor);
            currentShape.setThickness(thickness);
            shapes.add(currentShape);
            int x1 = e.getX();
            int y1 = e.getY();

            currentShape.setStartX(X2);
            currentShape.setStartY(Y2);
            currentShape.setEndX(x1);
            currentShape.setEndY(y1);
            repaint();
            X2 = x1;
            Y2 = y1;
        }

    }

    class RectangleMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            currentShape = new Rectangle();
            currentShape.setColor(currentColor);
            currentShape.setThickness(thickness);
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
            currentShape.setEndX(event.getX());
            currentShape.setEndY(event.getY());
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            currentShape.setEndX(event.getX());
            currentShape.setEndY(event.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            shapes.add(currentShape);
            repaint();
        }

    }

    class EllipseMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            currentShape = new Ellipse();
            currentShape.setColor(currentColor);
            currentShape.setThickness(thickness);
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
            currentShape.setEndX(event.getX());
            currentShape.setEndY(event.getY());
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            currentShape.setEndX(event.getX());
            currentShape.setEndY(event.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            shapes.add(currentShape);
            repaint();
        }

    }

    class LineMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            currentShape = new Line();
            currentShape.setColor(currentColor);
            currentShape.setThickness(thickness);
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
            currentShape.setEndX(event.getX());
            currentShape.setEndY(event.getY());
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            currentShape.setEndX(event.getX());
            currentShape.setEndY(event.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            shapes.add(currentShape);
            repaint();
        }

    }

    class EraseMouseEvent extends MouseInputAdapter {
        private int X2;
        private int Y2;

        @Override
        public void mousePressed(MouseEvent e) {
            X2 = e.getX();
            Y2 = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape = new Pencil();
            currentShape.setThickness(thickness);
            currentShape.setColor(Color.white);
            shapes.add(currentShape);
            int x1 = e.getX();
            int y1 = e.getY();

            currentShape.setStartX(X2);
            currentShape.setStartY(Y2);
            currentShape.setEndX(x1);
            currentShape.setEndY(y1);
            repaint();
            X2 = x1;
            Y2 = y1;
        }

    }

}
