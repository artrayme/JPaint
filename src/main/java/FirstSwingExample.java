import MainWindow.MainWindow;

import javax.swing.*;

public class FirstSwingExample {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setBounds(100, 100, 400, 500);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}  