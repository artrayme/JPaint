package mainwindow;

import mainwindow.shapes.Ellipse;
import mainwindow.shapes.Line;
import mainwindow.shapes.Rectangle;
import mainwindow.shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class DrawArea extends JPanel {
    private boolean trackMouse = false;

    LinkedList<Shape> shapes = new LinkedList<>();

    Shape currentShape;

    public DrawArea() {
        init();
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (!trackMouse) {
                    setCurrentShape();
                    currentShape.setStartX(event.getX());
                    currentShape.setStartY(event.getY());
                    currentShape.setEndX(event.getX());
                    currentShape.setEndY(event.getY());
                    trackMouse = true;
                } else {

                    currentShape.setEndX(event.getX());
                    currentShape.setEndY(event.getY());
                    shapes.add(currentShape);
                    repaint();
                    trackMouse = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
//                super.mouseReleased(mouseEvent);
            }


        });

        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent event) {
                System.out.println("mouse moved");
                if (trackMouse) {
                    currentShape.setEndX(event.getX());
                    currentShape.setEndY(event.getY());
                    repaint();
                }
            }

//            @Override
//            public void mouseDragged(MouseEvent mouseEvent) {
//                if (MainWindow.currentInstrument.equals("pencil")){
//
//                }
//            }
        });

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

    private void setCurrentShape() {
        switch (MainWindow.currentInstrument) {
            case "pencil": {
                break;
            }
            case "line": {
                currentShape = new Line();
                break;
            }
            case "rectangle": {
                currentShape = new Rectangle();
                break;
            }
            case "ellipse": {
                currentShape = new Ellipse();
                break;
            }
            case "erase": {
                break;
            }
            case "selection": {
                break;
            }
            case "lasso": {
                break;
            }
        }
    }
}
