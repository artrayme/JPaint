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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class DrawArea extends JPanel {
    //This map is using for adding shortcuts (only copy-paste now)
    private final HashMap<KeyStroke, Action> actionMap = new HashMap<>();

    //This MouseInputAdapter set behavior for each figure when draw
    private MouseInputAdapter currentEvent = new PencilMouseEvent();

    //Current list of shapes. All shapes from this list are repaints each cycle
    private final LinkedList<Shape> shapes = new LinkedList<>();

    //This shape is consist of temporary objects, such as selection frame.
    // Shapes in this list are display, but not saved to image
    private final LinkedList<Shape> tempShapes = new LinkedList<>();

    //Current active shape. It's redraws too, and using for the figure animation
    private Shape currentShape = new Pencil();

    private Color currentColor = Color.BLACK;
    private int thickness = 1;
    private int zoom = 1;

    //Dimensions for a selection rectangle
    private int selectedAreaStartX, selectedAreaStartY, selectedAreaEndX, selectedAreaEndY;

    //Image of current drawing area state
    private Image image;

    //This image is using for copy-paste and zoom functions
    private Image croppedImage;

    //This is a duplicate of the drawing area
    private Graphics2D graphics;

    //This field is appear, when text function is activated
    private final JTextField textField = new JTextField();

    public DrawArea() {
        setupShortcuts();
        pencil();
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
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        getScreenImage();
        for (Shape shape : shapes) {
            shape.draw(graphics);
            shape.draw(g);
        }
        for (Shape tempShape : tempShapes) {
            tempShape.draw(g);
        }
        currentShape.draw(g);
    }

    private void getScreenImage() {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.white);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            repaint();
        }
    }

    public void pencil() {
        clearLastFigure();
        currentEvent = new PencilMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void line() {
        clearLastFigure();
        currentEvent = new LineMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void rectangle() {
        clearLastFigure();
        currentEvent = new RectangleMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void ellipse() {
        clearLastFigure();
        currentEvent = new EllipseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void text() {
        clearLastFigure();
        currentEvent = new TextMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public void erase() {
        clearLastFigure();
        removeMouseMotionListener(currentEvent);
        currentEvent = new EraseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public void selection() {
        clearLastFigure();
        currentEvent = new SelectionMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    public void lasso() {
        clearLastFigure();
        currentEvent = new LassoMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    public void picture() {
        clearLastFigure();
        currentEvent = new PictureMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        currentShape = new Picture(croppedImage);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void zoom() {
        clearLastFigure();
        currentEvent = new ZoomMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        croppedImage = copyImage(((BufferedImage) image).getSubimage(0, 0, 100 / zoom, 100 / zoom));
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

    private void clearLastFigure() {
        tempShapes.clear();
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        repaint();
    }

    private BufferedImage copyImage(Image img) {
        BufferedImage copyOfImage = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return copyOfImage;
    }

    private void cacheField() {
        repaint();
        shapes.clear();
        Image newBackground = copyImage(image);
        shapes.add(new Picture(0, 0, newBackground));
        repaint();
    }

    private class PencilMouseEvent extends MouseInputAdapter {
        private int X2;
        private int Y2;

        @Override
        public void mousePressed(MouseEvent e) {
            X2 = e.getX();
            Y2 = e.getY();
            currentShape = new Pencil(X2, Y2, X2, Y2, currentColor, thickness);
            shapes.add(currentShape);
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x1 = e.getX();
            int y1 = e.getY();
            currentShape = new Pencil(X2, Y2, x1, y1, currentColor, thickness);
            shapes.add(currentShape);
            repaint();
            X2 = x1;
            Y2 = y1;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            cacheField();
        }

    }

    private class LineMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            cacheField();
            currentShape = new Line(e.getX(), e.getY(), e.getX(), e.getY(), currentColor, thickness);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape.setEndX(e.getX());
            currentShape.setEndY(e.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            shapes.add(currentShape);
            repaint();
        }

    }

    private class RectangleMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            cacheField();
            currentShape = new Rectangle(e.getX(), e.getY(), e.getX(), e.getY(), currentColor, thickness);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape.setEndX(e.getX());
            currentShape.setEndY(e.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            shapes.add(currentShape);
            repaint();
        }

    }

    private class EllipseMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            cacheField();

            currentShape = new Ellipse(e.getX(), e.getY(), e.getX(), e.getY(), currentColor, thickness);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape.setEndX(e.getX());
            currentShape.setEndY(e.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            shapes.add(currentShape);
            repaint();
        }

    }

    private class TextMouseEvent extends MouseInputAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            cacheField();
            tempShapes.clear();
            currentShape = new SelectionRectangle(e.getX(), e.getY(), e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape.setEndX(e.getX());
            currentShape.setEndY(e.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            tempShapes.add(currentShape);
            int startX = Math.min(currentShape.getStartX(), currentShape.getEndX());
            int startY = Math.min(currentShape.getStartY(), currentShape.getEndY());
            addTextField(startX, startY);
            currentShape = new Text();
            currentShape.setColor(currentColor);
            currentShape.setStartX(startX);
            currentShape.setStartY(startY + textField.getFont().getSize());
        }

        private void addTextField(int startX, int startY) {
            textField.setText("");
            int width = Math.abs(currentShape.getEndX() - currentShape.getStartX());
            textField.setBounds(startX, startY - 20, width, 20);
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
            X2 = e.getX();
            Y2 = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x1 = e.getX();
            int y1 = e.getY();
            currentShape = new Pencil(X2, Y2, x1, y1, Color.white, thickness);
            shapes.add(currentShape);
            repaint();
            X2 = x1;
            Y2 = y1;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            cacheField();
        }

    }

    private class SelectionMouseEvent extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            tempShapes.clear();
            currentShape = new SelectionRectangle(e.getX(), e.getY(), e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape.setEndX(e.getX());
            currentShape.setEndY(e.getY());
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            selectedAreaStartX = currentShape.getStartX();
            selectedAreaStartY = currentShape.getStartY();
            selectedAreaEndX = currentShape.getEndX();
            selectedAreaEndY = currentShape.getEndY();
            tempShapes.add(currentShape);
        }
    }

    private class LassoMouseEvent extends MouseInputAdapter {
        private int X2, Y2;
        private int minX = getWidth(), minY = getHeight(), maxX = 0, maxY = 0;

        @Override
        public void mousePressed(MouseEvent e) {
            tempShapes.clear();
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
            int x1 = e.getX();
            int y1 = e.getY();
            setMinMax(e);
            currentShape = new Pencil(X2, Y2, x1, y1, Color.black, 1);
            tempShapes.add(currentShape);
            repaint();
            X2 = x1;
            Y2 = y1;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            tempShapes.clear();
            currentShape = new SelectionRectangle(minX, minY, maxX, maxY);
            selectedAreaStartX = minX;
            selectedAreaStartY = minY;
            selectedAreaEndX = maxX;
            selectedAreaEndY = maxY;
            tempShapes.add(currentShape);
            cacheField();
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
        public void mousePressed(MouseEvent e) {
            cacheField();
            shapes.add(currentShape);
            currentShape = new Picture(croppedImage);
            currentShape.setStartX(e.getX());
            currentShape.setStartY(e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            currentShape.setStartX(e.getX());
            currentShape.setStartY(e.getY());
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            currentShape.setStartX(e.getX());
            currentShape.setStartY(e.getY());
            repaint();
        }

    }

    private class ZoomMouseEvent extends MouseInputAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getX() + 100 <= ((BufferedImage) image).getWidth() && e.getY() + 100 <= ((BufferedImage) image).getHeight()) {
                croppedImage = copyImage(((BufferedImage) image).getSubimage(e.getX(), e.getY(), 100 / zoom, 100 / zoom));
                croppedImage = croppedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                currentShape = new Picture(croppedImage);
                currentShape.setStartX(e.getX());
                currentShape.setStartY(e.getY());
                repaint();
            }
        }

    }

    private class CopyKeyEvent extends AbstractAction {
        public CopyKeyEvent() {
            super("copy");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (!tempShapes.isEmpty()) {
                int startX = Math.min(selectedAreaStartX, selectedAreaEndX);
                int startY = Math.min(selectedAreaStartY, selectedAreaEndY);
                int width = Math.abs(selectedAreaEndX - selectedAreaStartX);
                int height = Math.abs(selectedAreaEndY - selectedAreaStartY);
                croppedImage = copyImage(((BufferedImage) image).getSubimage(startX, startY, width, height));
                croppedImage = croppedImage.getScaledInstance(width, height, Image.SCALE_FAST);
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
            FontRenderContext frc = graphics.getFontRenderContext();
            int width = (int) textField.getFont().getStringBounds(textField.getText(), frc).getWidth();
            if (width > textField.getWidth()) {
                textField.setBackground(Color.red);
            } else {
                textField.setBackground(Color.white);
            }
        }
    }

    private class TextFieldKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                FontRenderContext frc = graphics.getFontRenderContext();
                int width = (int) textField.getFont().getStringBounds(textField.getText(), frc).getWidth();
                if (width < textField.getWidth()) {
                    shapes.add(currentShape);
                    remove(textField);
                    tempShapes.clear();
                    repaint();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
        }
    }

}