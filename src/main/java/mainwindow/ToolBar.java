package mainwindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    private final JButton pencil = new JButton();
    private final JButton line = new JButton();
    private final JButton rectangle = new JButton();
    private final JButton ellipse = new JButton();
    private final JButton erase = new JButton();
    private final JButton selection = new JButton();
    private final JButton lasso = new JButton();

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
        initEraseButton();
        initSelectionButton();
        initLassoButton();
    }

    private void addAll() {
        add(pencil);
        add(line);
        add(rectangle);
        add(ellipse);
        add(new JToolBar.Separator());
        add(erase);
        add(new JToolBar.Separator());
        add(selection);
        add(lasso);
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
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).pencil();
        });
    }

    private void initLineButton() {
        line.setIcon(initButtonIcon("/icons/line.png"));
        line.setBackground(new Color(220, 220, 220));
        line.addActionListener(actionEvent -> {
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).line();
        });
    }

    private void initEllipseButton() {
        ellipse.setIcon(initButtonIcon("/icons/ellipse.png"));
        ellipse.setBackground(new Color(220, 220, 220));
        ellipse.addActionListener(actionEvent -> {
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).ellipse();
        });
    }

    private void initRectangleButton() {
        rectangle.setIcon(initButtonIcon("/icons/rectangle.png"));
        rectangle.setBackground(new Color(220, 220, 220));
        rectangle.addActionListener(actionEvent -> {
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).rectangle();
        });
    }

    private void initEraseButton() {
        erase.setIcon(initButtonIcon("/icons/erase.png"));
        erase.setBackground(new Color(220, 220, 220));
        erase.addActionListener(actionEvent -> {
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).erase();
        });
    }


    private void initSelectionButton() {
        selection.setIcon(initButtonIcon("/icons/selection.png"));
        selection.setBackground(new Color(220, 220, 220));
        selection.addActionListener(actionEvent -> {
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).selection();
        });
    }


    private void initLassoButton() {
        lasso.setIcon(initButtonIcon("/icons/lasso.png"));
        lasso.setBackground(new Color(220, 220, 220));
        lasso.addActionListener(actionEvent -> {
            ((DrawArea)MainWindow.tabBar.getSelectedComponent()).lasso();
        });
    }

}
