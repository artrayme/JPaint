package mainwindow.drawingarea;

import shapes.Rectangle;
import shapes.Shape;
import shapes.*;

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
    /**
     * This map is using for adding shortcuts (only copy-paste now)
     */
    private final HashMap<KeyStroke, Action> actionMap = new HashMap<>();

    /**
     * This MouseInputAdapter set behavior for each figure when draw
     */
    private MouseInputAdapter currentEvent = new PencilMouseEvent();

    /**
     * Current list of shapes. All shapes from this list are repaints each cycle
     * Shapes from this list are real (all shapes, except selection area and lasso)
     */
    private final LinkedList<Shape> shapes = new LinkedList<>();

    /**
     * This list is consist of temporary shapes.
     * Shapes in this list are display, but not saved to image (like selection area and lasso)
     */
    private final LinkedList<Shape> tempShapes = new LinkedList<>();

    /**
     * Current active shape. It's also drawn every cycle, and using for the figure drawing animation
     */
    private Shape currentShape = new Pencil();

    /**
     * The Current color of the figures
     */
    private Color currentColor = Color.BLACK;

    /**
     * The current thickness of the figures
     */
    private int thickness = 1;

    /**
     * The current zoom level of the zoom tool
     */
    private int zoom = 1;

    /**
     * The dimensions of the selection rectangle
     */
    private int selectedAreaStartX, selectedAreaStartY, selectedAreaEndX, selectedAreaEndY;

    /**
     * An image that duplicates the current state of the field.
     * Is using for an optimization: if user will draw a lot of shapes,
     * the program can become slow (because every cycle all the figures from shapes list
     * will be drawn). To solve this problem, sometimes all the field figures are saved as an image,
     * and the result image becomes a figure.
     */
    private Image fieldImage;

    /**
     * This image is using for copy-paste and zoom functions
     */
    private Image croppedImage;

    /**
     * This is using for generating the fieldImage
     */
    private Graphics2D fieldGraphicsDuplicate;

    /**
     * This field is appear, when a text function is activated
     */
    private final JTextField textField = new JTextField();

    public DrawArea() {
        setupShortcuts();
        pencil();
    }

    /**
     * In this method shortcuts are initialized
     */
    private void setupShortcuts() {

        KeyStroke copyKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke pasteKey = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
        actionMap.put(copyKey, new CopyKeyEvent());
        actionMap.put(pasteKey, new PasteKeyEvent());

        addShortcutsEngine();
    }

    /**
     * Init a keyboard event listener
     */
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

    /**
     * Setter for the current shapes thickness
     *
     * @param thickness
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * Setter for the current shapes thickness
     *
     * @param currentColor
     */
    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * Setter for the current zoom tool
     *
     * @param zoom
     */
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    /**
     * The Main drawing cycle.
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //filling background
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        //generating filedImage
        getScreenImage();

        //Drawing real shapes
        for (Shape shape : shapes) {
            shape.draw(fieldGraphicsDuplicate);
            shape.draw(g);
        }

        //Drawing temporary shapes
        for (Shape tempShape : tempShapes) {
            tempShape.draw(g);
        }

        //Drawing currentShape;
        currentShape.draw(g);
    }

    /**
     * Generating the fieldImage
     */
    private void getScreenImage() {
        if (fieldImage == null) {
            fieldImage = createImage(getSize().width, getSize().height);
            fieldGraphicsDuplicate = (Graphics2D) fieldImage.getGraphics();
            fieldGraphicsDuplicate.setColor(Color.white);
            fieldGraphicsDuplicate.fillRect(0, 0, getWidth(), getHeight());
            repaint();
        }
    }

    /**
     * Init the pencil tool
     */
    public void pencil() {
        clearLastFigure();
        currentEvent = new PencilMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Init the line tool
     */
    public void line() {
        clearLastFigure();
        currentEvent = new LineMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Init the rectangle tool
     */
    public void rectangle() {
        clearLastFigure();
        currentEvent = new RectangleMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    /**
     * Init the ellipse tool
     */
    public void ellipse() {
        clearLastFigure();
        currentEvent = new EllipseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    /**
     * Init the text tool
     */
    public void text() {
        clearLastFigure();
        currentEvent = new TextMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    /**
     * Init the pencil tool
     */
    public void erase() {
        clearLastFigure();
        removeMouseMotionListener(currentEvent);
        currentEvent = new EraseMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    /**
     * Init the selection tool
     */
    public void selection() {
        clearLastFigure();
        currentEvent = new SelectionMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    /**
     * Init the lasso tool
     */
    public void lasso() {
        clearLastFigure();
        currentEvent = new LassoMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    }

    /**
     * Init the picture tool
     */
    public void picture() {
        clearLastFigure();
        currentEvent = new PictureMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        currentShape = new Picture(croppedImage);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    /**
     * Init the zoom tool
     */
    public void zoom() {
        clearLastFigure();
        currentEvent = new ZoomMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));
        croppedImage = copyImage(((BufferedImage) fieldImage).getSubimage(0, 0, 100 / zoom, 100 / zoom));
        croppedImage = croppedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        currentShape = new Picture(croppedImage);
    }

    /**
     * Loading picture from file
     *
     * @param file file from which the reading will be performed
     */
    public void loadPicture(File file) {
        try {
            croppedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture();
    }

    /**
     * Saving picture to the file
     *
     * @param file
     */
    public void savePicture(File file) {
        try {
            ImageIO.write((RenderedImage) fieldImage, "PNG", file);
        } catch (IOException ignored) {
        }
    }

    /**
     * Clear last figure and connected with figure components
     * Such as mouse listener, tempShapes and etc.
     */
    private void clearLastFigure() {
        tempShapes.clear();
        remove(textField);
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        repaint();
    }

    /**
     * Copying any image
     *
     * @param img input image
     * @return copy of the input image
     */
    private BufferedImage copyImage(Image img) {
        BufferedImage copyOfImage = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return copyOfImage;
    }

    /**
     * Optimizations. This method is using for clearing shapes list
     */
    private void cacheField() {
        repaint();
        shapes.clear();
        Image newBackground = copyImage(fieldImage);
        shapes.add(new Picture(0, 0, newBackground));
        repaint();
    }

    /**
     * An event listener for the pencil tool
     */
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

    /**
     * An event listener for the line tool
     */
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

    /**
     * An event listener for the rectangle tool
     */
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

    /**
     * An event listener for the ellipse tool
     */
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

    /**
     * An event listener for the text tool
     */
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

    /**
     * An event listener for the erase tool
     */
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

    /**
     * An event listener for the selection tool
     */
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

    /**
     * An event listener for the lasso tool
     */
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

    /**
     * An event listener for the picture tool
     */
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

    /**
     * An event listener for the zoom tool
     */
    private class ZoomMouseEvent extends MouseInputAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getX() + 100 <= ((BufferedImage) fieldImage).getWidth() && e.getY() + 100 <= ((BufferedImage) fieldImage).getHeight()) {
                croppedImage = copyImage(((BufferedImage) fieldImage).getSubimage(e.getX(), e.getY(), 100 / zoom, 100 / zoom));
                croppedImage = croppedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                currentShape = new Picture(croppedImage);
                currentShape.setStartX(e.getX());
                currentShape.setStartY(e.getY());
                repaint();
            }
        }

    }

    /**
     * An event listener for the Copy shortcut
     */
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
                croppedImage = copyImage(((BufferedImage) fieldImage).getSubimage(startX, startY, width, height));
                croppedImage = croppedImage.getScaledInstance(width, height, Image.SCALE_FAST);
            }
        }
    }

    /**
     * An event listener for the Paste shortcut
     */
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

    /**
     * An event listener for the text field (text tool)
     */
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
            FontRenderContext frc = fieldGraphicsDuplicate.getFontRenderContext();
            int width = (int) textField.getFont().getStringBounds(textField.getText(), frc).getWidth();
            if (width > textField.getWidth()) {
                textField.setBackground(Color.red);
            } else {
                textField.setBackground(Color.white);
            }
        }
    }

    /**
     * An event listener for the text field (text tool)
     */
    private class TextFieldKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent keyEvent) {
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                FontRenderContext frc = fieldGraphicsDuplicate.getFontRenderContext();
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