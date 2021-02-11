package MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {
    public static String currentInstrument = "pencil";
    private final JButton pencil = new JButton();
    private final JButton line = new JButton();
    private final JButton rectangle = new JButton();
    private final JButton ellipse = new JButton();
    private final JButton erase = new JButton();
    private final JButton colors = new JButton();
    private final JButton selection = new JButton();
    private final JButton lasso = new JButton();

    public ToolBar(){
        setBackground(Color.darkGray);
        initToolBar();
    }
    private void initToolBar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        initPencilButton();
        initLineButton();
        initRectangleButton();
        initEllipseButton();
        initEraseButton();
        initColorsButton();
        initSelectionButton();
        initLassoButton();
        add(pencil);
        add(line);
        add(rectangle);
        add(ellipse);
        add(new JToolBar.Separator());
        add(erase);
        add(new JToolBar.Separator());
        add(colors);
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
    }

    private void initLineButton() {
        line.setIcon(initButtonIcon("/icons/line.png"));
        line.setBackground(new Color(220, 220, 220));

    }

    private void initEllipseButton() {
        ellipse.setIcon(initButtonIcon("/icons/ellipse.png"));
        ellipse.setBackground(new Color(220, 220, 220));
    }

    private void initRectangleButton() {
        rectangle.setIcon(initButtonIcon("/icons/rectangle.png"));
        rectangle.setBackground(new Color(220, 220, 220));
    }

    private void initEraseButton() {
        erase.setIcon(initButtonIcon("/icons/erase.png"));
        erase.setBackground(new Color(220, 220, 220));
    }

    private void initColorsButton() {
        colors.setIcon(initButtonIcon("/icons/color.png"));
        colors.setBackground(new Color(220, 220, 220));
    }

    private void initSelectionButton() {
        selection.setIcon(initButtonIcon("/icons/selection.png"));
        selection.setBackground(new Color(220, 220, 220));
    }


    private void initLassoButton() {
        lasso.setIcon(initButtonIcon("/icons/lasso.png"));
        lasso.setBackground(new Color(220, 220, 220));
    }

}
