import mainwindow.MainWindow;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

//Liberation Serif
//Liberation Mono

public class Main {

    public static void main(String[] args) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);

//      force anti-aliasing if it is disabled in the jvm configuration
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        setUIFont(new FontUIResource("Liberation Serif", Font.BOLD, 14));

        MainWindow mainWindow = new MainWindow();
        mainWindow.setTitle("JPaint - 1.2 beta");
        mainWindow.setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource)
                UIManager.put(key, f);
        }
    }

}
