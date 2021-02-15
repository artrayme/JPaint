package mainwindow;

import mainwindow.shapes.Rectangle;
import mainwindow.shapes.Shape;
import mainwindow.shapes.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class DrawArea extends JComponent {
    private final HashMap<KeyStroke, Action> actionMap = new HashMap<>();
    private MouseInputAdapter currentEvent = new PencilMouseEvent();
    private final LinkedList<Shape> shapes = new LinkedList<>();
    private Shape currentShape = new Pencil();
    private Color currentColor = Color.BLACK;
    private int thickness = 1;
    private boolean isSelection = false;
    private int selectedAreaStartX, selectedAreaStartY, selectedAreaEndX, selectedAreaEndY;
    private Image image;
    private Image croppedImage;
    private Graphics2D graphics;

    public DrawArea() {
        setupShortcuts();
        pencil();
    }

    private void setupShortcuts() {
        KeyStroke copyKey = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
        KeyStroke pasteKey = KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK);
        actionMap.put(copyKey, new CopyKeyEvent());
        actionMap.put(pasteKey, new PasteKeyEvent());

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        getScreen();
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Shape shape : shapes) {
            Graphics2D g2d = graphics;
            shape.draw(g2d);
            shape.draw(g);
        }
        currentShape.draw(g);
    }

    private void getScreen() {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics = (Graphics2D) image.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setPaint(Color.white);
            graphics.fillRect(0, 0, getSize().width, getSize().height);
            graphics.setPaint(Color.black);
            repaint();
        }
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
        currentEvent = new SelectionMouseEvent();
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

    public void picture() {
        removeMouseListener(currentEvent);
        removeMouseMotionListener(currentEvent);
        currentEvent = new PictureMouseEvent();
        addMouseListener(currentEvent);
        addMouseMotionListener(currentEvent);
        currentShape = new Picture(croppedImage);
    }

    public void loadPicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                image = ImageIO.read(selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    class PencilMouseEvent extends MouseInputAdapter {
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

    class RectangleMouseEvent extends MouseInputAdapter {

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

    class EllipseMouseEvent extends MouseInputAdapter {

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

    class LineMouseEvent extends MouseInputAdapter {

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

    class EraseMouseEvent extends MouseInputAdapter {
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

    class SelectionMouseEvent extends MouseInputAdapter {

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

            repaint();
        }
    }

    class PictureMouseEvent extends MouseInputAdapter {

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

        @Override
        public void mouseReleased(MouseEvent event) {
//            picture();
        }

    }

    class CopyKeyEvent extends AbstractAction {
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

    class PasteKeyEvent extends AbstractAction {
        public PasteKeyEvent() {
            super("paste");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (croppedImage != null)
                picture();
        }
    }

}