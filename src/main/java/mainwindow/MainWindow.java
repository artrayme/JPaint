package mainwindow;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static String currentInstrument = "pencil";
    public static Color currentColor = new Color(0,0,0);

    public static final JTabbedPane tabBar = new JTabbedPane();

    public final MenuBar menuBar = new MenuBar();
    public final ToolBar toolBar = new ToolBar();
    public final ConfigurationBar configurationBar = new ConfigurationBar();
    public static int tabsCount = 0;

    public MainWindow() {
        setLayout(new BorderLayout());
        this.setJMenuBar(menuBar);
        tabBar.add("untiled"+tabsCount++, new DrawArea());

        this.add(toolBar, BorderLayout.NORTH);
        this.add(configurationBar, BorderLayout.WEST);
        this.add(tabBar, BorderLayout.CENTER);
    }

}
