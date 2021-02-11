package MainWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final MenuBar menuBar = new MenuBar();
    private final JTabbedPane tabBar = new JTabbedPane();
    private final ToolBar toolBar = new ToolBar();
    private final JColorChooser colorChooser = new JColorChooser();

    public MainWindow() {

        setLayout(new BorderLayout());
        initMenuBar();
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


}
