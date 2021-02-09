package MainWindow;

import javax.swing.*;

public class MainWindow extends JFrame {
    private final MenuBar menuBar = new MenuBar();
    private final JTabbedPane tabBar = new JTabbedPane();

    public MainWindow() {
        this.setJMenuBar(menuBar);
        this.add(tabBar);
        tabBar.add(new DrawArea());
    }
}
