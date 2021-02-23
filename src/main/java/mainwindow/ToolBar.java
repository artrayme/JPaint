package mainwindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    private final JButton pencil = new JButton();
    private final JButton line = new JButton();
    private final JButton rectangle = new JButton();
    private final JButton ellipse = new JButton();
    private final JButton text = new JButton();
    private final JButton erase = new JButton();
    private final JButton selection = new JButton();
    private final JButton lasso = new JButton();
    private final JButton zoom = new JButton();

    private JButton lastClicked = pencil;

    public ToolBar() {
        setBackground(Color.darkGray);
        initToolBar();
    }

    private void initToolBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        initAll();
        addAll();
    }

    private void initAll() {
        initPencilButton();
        initLineButton();
        initRectangleButton();
        initEllipseButton();
        initTextButton();
        initEraseButton();
        initSelectionButton();
        initLassoButton();
        initZoomButton();
    }

    private void addAll() {
        add(pencil);
        add(line);
        add(rectangle);
        add(ellipse);
        add(text);
        add(new JToolBar.Separator());
        add(erase);
        add(new JToolBar.Separator());
        add(selection);
        add(lasso);
        add(new JToolBar.Separator());
        add(zoom);
    }

    private ImageIcon initButtonIcon(String path) {
        Image dimg = null;
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            dimg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        assert dimg != null;
        return new ImageIcon(dimg);
    }

    private void initPencilButton() {
        pencil.setIcon(initButtonIcon("/icons/pencil.png"));
        pencil.setBackground(new Color(220, 220, 220));
        pencil.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).pencil();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = pencil;
            pencil.setBackground(new Color(118, 246, 74));
        });
    }

    private void initLineButton() {
        line.setIcon(initButtonIcon("/icons/line.png"));
        line.setBackground(new Color(220, 220, 220));
        line.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).line();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = line;
            line.setBackground(new Color(118, 246, 74));
        });
    }

    private void initEllipseButton() {
        ellipse.setIcon(initButtonIcon("/icons/ellipse.png"));
        ellipse.setBackground(new Color(220, 220, 220));
        ellipse.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).ellipse();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = ellipse;
            ellipse.setBackground(new Color(118, 246, 74));
        });
    }

    private void initRectangleButton() {
        rectangle.setIcon(initButtonIcon("/icons/rectangle.png"));
        rectangle.setBackground(new Color(220, 220, 220));
        rectangle.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).rectangle();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = rectangle;
            rectangle.setBackground(new Color(118, 246, 74));
        });
    }

    private void initTextButton() {
        text.setIcon(initButtonIcon("/icons/text.png"));
        text.setBackground(new Color(220, 220, 220));
        text.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).text();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = text;
            text.setBackground(new Color(118, 246, 74));
        });
    }

    private void initEraseButton() {
        erase.setIcon(initButtonIcon("/icons/erase.png"));
        erase.setBackground(new Color(220, 220, 220));
        erase.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).erase();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = erase;
            erase.setBackground(new Color(118, 246, 74));
        });
    }

    private void initSelectionButton() {
        selection.setIcon(initButtonIcon("/icons/selection.png"));
        selection.setBackground(new Color(220, 220, 220));
        selection.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).selection();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = selection;
            selection.setBackground(new Color(118, 246, 74));
        });
    }

    private void initLassoButton() {
        lasso.setIcon(initButtonIcon("/icons/lasso.png"));
        lasso.setBackground(new Color(220, 220, 220));
        lasso.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).lasso();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = lasso;
            lasso.setBackground(new Color(118, 246, 74));
        });
    }

    private void initZoomButton() {
        zoom.setIcon(initButtonIcon("/icons/zoom.png"));
        zoom.setBackground(new Color(220, 220, 220));
        zoom.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).zoom();
            lastClicked.setBackground(new Color(220, 220, 220));
            lastClicked = zoom;
            zoom.setBackground(new Color(118, 246, 74));
        });
    }

}
