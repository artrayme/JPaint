import MainWindow.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class FirstSwingExample {

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth()*0.8);
        int height = (int)(screenSize.getHeight()*0.8);

        setUIFont(new javax.swing.plaf.FontUIResource("Times New Roman",Font.BOLD,14));

        MainWindow mainWindow = new MainWindow();
        mainWindow.setBounds((screenSize.width-width)/2, (screenSize.height-height)/2, width, height);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
}
