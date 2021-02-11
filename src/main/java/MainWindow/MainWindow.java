package MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final MenuBar menuBar = new MenuBar();
    private final JTabbedPane tabBar = new JTabbedPane();
    private final JPanel toolBar = new JPanel();
    private final JColorChooser colorChooser = new JColorChooser();

    public MainWindow() {
        setLayout(new BorderLayout());
        initMenuBar();
        initToolBar();
        initTabBar();
        this.setJMenuBar(menuBar);
        this.add(toolBar, BorderLayout.NORTH);
        this.add(tabBar, BorderLayout.CENTER);
    }

    private void initMenuBar() {

    }

    private void initTabBar() {
        tabBar.add("untiled1", new DrawArea());
        tabBar.add("untiled2", new DrawArea());
        tabBar.add("untiled3", new DrawArea());
    }

    private void initToolBar() {
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
        JButton pencil = initPencilButton();
        JButton line = initLineButton();
        JButton rectangle = initRectangleButton();
        JButton ellipse = initEllipseButton();
        JButton erase = initEraseButton();
        JButton colors = initColorsButton();
        JButton selection = initSelectionButton();
        JButton lasso = initLassoButton();
        toolBar.add(pencil);
        toolBar.add(line);
        toolBar.add(rectangle);
        toolBar.add(ellipse);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(erase);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(colors);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(selection);
        toolBar.add(lasso);
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

    private JButton initPencilButton() {
        JButton pencil = new JButton();
        pencil.setIcon(initButtonIcon("/icons/pencil.png"));
        return pencil;
    }

    private JButton initLineButton() {
        JButton line = new JButton();
        line.setIcon(initButtonIcon("/icons/line.png"));
        return line;
    }

    private JButton initEllipseButton() {
        JButton ellipse = new JButton();
        ellipse.setIcon(initButtonIcon("/icons/ellipse.png"));
        return ellipse;
    }

    private JButton initRectangleButton() {
        JButton rectangle = new JButton();
        rectangle.setIcon(initButtonIcon("/icons/rectangle.png"));
        return rectangle;
    }

    private JButton initEraseButton() {
        JButton erase = new JButton();
        erase.setIcon(initButtonIcon("/icons/erase.png"));
        return erase;
    }

    private JButton initColorsButton() {
        JButton colors = new JButton();
        colors.setIcon(initButtonIcon("/icons/color.png"));
        return colors;
    }

    private JButton initSelectionButton() {
        JButton selection = new JButton();
        selection.setIcon(initButtonIcon("/icons/selection.png"));
        return selection;
    }


    private JButton initLassoButton() {
        JButton lasso = new JButton();
        lasso.setIcon(initButtonIcon("/icons/lasso.png"));
        return lasso;
    }


}
