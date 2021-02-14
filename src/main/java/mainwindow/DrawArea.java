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
    LinkedList<Shape> shapes = new LinkedList<>();
    Shape currentShape;
    Color currentColor = Color.BLACK;

    public DrawArea() {
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
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
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
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
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
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
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

}
