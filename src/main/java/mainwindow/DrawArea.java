package mainwindow;

import mainwindow.shapes.Rectangle;
import mainwindow.shapes.Shape;
import mainwindow.shapes.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class DrawArea extends JPanel {
    private final HashMap<KeyStroke, Action> actionMap = new HashMap<>();
    private MouseInputAdapter currentEvent = new PencilMouseEvent();
    private final LinkedList<Shape> shapes = new LinkedList<>();

    private Shape currentShape = new Pencil();
    private Color currentColor = Color.BLACK;
    private int thickness = 1;
    private int zoom = 1;
    private boolean isSelection = false;

    private int selectedAreaStartX, selectedAreaStartY, selectedAreaEndX, selectedAreaEndY;
    private Image image;
    private Image croppedImage;
    private Graphics2D graphics;

    private final JTextField textField = new JTextField();

    public DrawArea() {
        setupShortcuts();
        pencil();
        Background background = new Background();
        background.setColor(Color.white);
        shapes.add(background);
    }

    private void setupShortcuts() {
        KeyStroke copyKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke pasteKey = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
        actionMap.put(copyKey, new CopyKeyEvent());
        actionMap.put(pasteKey, new PasteKeyEvent());

        addShortcutsEngine();
    }

    private void addShortcutsEngine() {
        KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kfm.addKeyEventDispatcher(e -> {
            KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
            if (actionMap.containsKey(keyStroke)) {
                final Action a = actionMap.get(keyStroke);
                final ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null);
                SwingUtilities.invokeLater(() -> a.actionPerformed(ae));
                return true;
            }
            return false;
        });
    }


    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        getScreenImage();
        shapes.get(0).setEndX(getWidth());
        shapes.get(0).setEndY(getHeight());
        for (Shape shape : shapes) {
            shape.draw(graphics);
            shape.draw(g);
        }
        currentShape.draw(g);
    }

    private void getScreenImage() {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D) image.getGraphics();
            repaint();
        }
    }

    public void pencil() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new PencilMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void line() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new LineMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void rectangle() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new RectangleMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void ellipse() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new EllipseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void text() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new TextMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public void erase() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new EraseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public void selection() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new SelectionMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    public void lasso() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new LassoMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    public void picture() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new PictureMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        currentShape = new Picture(croppedImage);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void zoom() {
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new ZoomMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        croppedImage = copyImage(((BufferedImage) image).getSubimage(0, 0, 100/zoom, 100/zoom));
        croppedImage = croppedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        currentShape = new Picture(croppedImage);
    }

    public void loadPicture(File file) {
        try {
            croppedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture();
    }

    public void savePicture(File file) {
        try {
            ImageIO.write((RenderedImage) image, "PNG", file);
        } catch (IOException ignored) {
        }
    }

    private BufferedImage copyImage(Image img) {
        BufferedImage copyOfImage = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return copyOfImage;
    }

    private class PencilMouseEvent extends MouseInputAdapter {
        private int X2;
        private int Y2;

        @Override
        public void mousePressed(MouseEvent e) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = false;
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

    private class LineMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = false;
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

    private class RectangleMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = false;
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

    private class EllipseMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = false;
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

    private class TextMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = true;
            currentShape = new SelectionRectangle();
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
            int startX = Math.min(currentShape.getStartX(), currentShape.getEndX());
            int startY = Math.min(currentShape.getStartY(), currentShape.getEndY());
            addTextField(startX, startY);
            currentShape = new Text();
            currentShape.setColor(currentColor);
            currentShape.setStartX(startX);
            currentShape.setStartY(startY+40);
            repaint();
        }

        private void addTextField(int startX, int startY){
            textField.setText("");
            int width = Math.abs(currentShape.getEndX()-currentShape.getStartX());
            textField.setBounds(startX, startY-20, width, 20);
            textField.addKeyListener(new TextFieldKeyListener());
            textField.getDocument().addDocumentListener(new TextFieldDocumentListener());
            add(textField);
            textField.requestFocus();
        }

    }

    private class EraseMouseEvent extends MouseInputAdapter {
        private int X2;
        private int Y2;

        @Override
        public void mousePressed(MouseEvent e) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = false;
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

    private class SelectionMouseEvent extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = true;
            currentShape = new SelectionRectangle();
            currentShape.setColor(Color.black);
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
            selectedAreaStartX = currentShape.getStartX();
            selectedAreaStartY = currentShape.getStartY();
            selectedAreaEndX = currentShape.getEndX();
            selectedAreaEndY = currentShape.getEndY();
            shapes.add(currentShape);

        }
    }

    private class LassoMouseEvent extends MouseInputAdapter {
        private int X2, Y2;
        private int minX = getWidth(), minY = getHeight(), maxX = 0, maxY = 0;
        private int shapesListSize;

        @Override
        public void mousePressed(MouseEvent e) {
            if (isSelection) {
                shapes.remove(shapes.size() - 1);
            }
            shapesListSize = shapes.size();
            isSelection = true;
            minX = getWidth();
            minY = getHeight();
            maxX = 0;
            maxY = 0;
            X2 = e.getX();
            Y2 = e.getY();
            setMinMax(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape = new Pencil();
            currentShape.setColor(currentColor);
            currentShape.setThickness(thickness);
            shapes.add(currentShape);
            int x1 = e.getX();
            int y1 = e.getY();
            setMinMax(e);
            currentShape.setStartX(X2);
            currentShape.setStartY(Y2);
            currentShape.setEndX(x1);
            currentShape.setEndY(y1);
            repaint();
            X2 = x1;
            Y2 = y1;
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            super.mouseReleased(mouseEvent);
            shapes.subList(shapesListSize, shapes.size()).clear();
            currentShape = new SelectionRectangle();
            currentShape.setStartX(minX);
            currentShape.setStartY(minY);
            currentShape.setEndX(maxX);
            currentShape.setEndY(maxY);
            selectedAreaStartX = currentShape.getStartX();
            selectedAreaStartY = currentShape.getStartY();
            selectedAreaEndX = currentShape.getEndX();
            selectedAreaEndY = currentShape.getEndY();
            shapes.add(currentShape);
            repaint();
        }

        private void setMinMax(MouseEvent e) {
            minX = Math.min(minX, e.getX());
            minY = Math.min(minY, e.getY());
            maxX = Math.max(maxX, e.getX());
            maxY = Math.max(maxY, e.getY());
        }
    }

    private class PictureMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            if (isSelection)
                shapes.remove(shapes.size() - 1);
            isSelection = false;
            shapes.add(currentShape);
            currentShape = new Picture(croppedImage);
        }

        @Override
        public void mouseMoved(MouseEvent event) {
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
            repaint();
        }

    }

    private class ZoomMouseEvent extends MouseInputAdapter {

        @Override
        public void mouseMoved(MouseEvent event) {
            croppedImage = copyImage(((BufferedImage) image).getSubimage(event.getX(), event.getY(), 100/zoom, 100/zoom));
            croppedImage = croppedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            currentShape = new Picture(croppedImage);
            currentShape.setStartX(event.getX());
            currentShape.setStartY(event.getY());
            repaint();
        }

    }

    private class CopyKeyEvent extends AbstractAction {
        public CopyKeyEvent() {
            super("copy");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (isSelection) {
                int startX = Math.min(selectedAreaStartX, selectedAreaEndX);
                int startY = Math.min(selectedAreaStartY, selectedAreaEndY);
                int width = Math.abs(selectedAreaEndX - selectedAreaStartX);
                int height = Math.abs(selectedAreaEndY - selectedAreaStartY);
                croppedImage = copyImage(((BufferedImage) image).getSubimage(startX + 1, startY + 1, width - 1, height - 1));
                croppedImage = croppedImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);

            }
        }
    }

    private class PasteKeyEvent extends AbstractAction {
        public PasteKeyEvent() {
            super("paste");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (croppedImage != null)
                picture();
        }
    }

    private class TextFieldDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            changeText();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            changeText();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            changeText();
        }

        private void changeText() {
            currentShape.setText(textField.getText());
            repaint();
        }
    }

    private class TextFieldKeyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
                int width = graphics.getFontMetrics().stringWidth(textField.getText());
                if (width<textField.getWidth()){
                    shapes.set(shapes.size()-1, currentShape);
                    isSelection = false;
                }
                else {
                    currentShape.setText("Err");
                }

                remove(textField);
                repaint();
            }

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
        }
    }

}