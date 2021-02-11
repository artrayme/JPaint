import MainWindow.MainWindow;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

//Liberation Serif
//Liberation Mono

public class Main {

    public static void main(String[] args) {
//        String fonts[] =
//                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//
//        for ( int i = 0; i < fonts.length; i++ )
//        {
//            System.out.println(fonts[i]);
//        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth()*0.8);
        int height = (int)(screenSize.getHeight()*0.8);

        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        System.out.println(UIManager.getCrossPlatformLookAndFeelClassName());
        setUIFont(new FontUIResource("Liberation Serif",Font.BOLD,14));

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
