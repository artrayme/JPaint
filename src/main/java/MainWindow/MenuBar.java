package MainWindow;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu editMenu = new JMenu("Edit");
    private final JMenu helpMenu = new JMenu("Help");

    public MenuBar(){
        initFileMenu();
        initEditMenu();
        initHelpMenu();
    }

    private void initFileMenu(){
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem close = new JMenuItem("Close");
        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(close);
        fileMenu.add(exit);
        this.add(fileMenu);
    }

    private void initEditMenu(){
        this.add(editMenu);
    }

    private void initHelpMenu(){
        this.add(helpMenu);

    }

}
