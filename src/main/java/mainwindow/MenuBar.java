package mainwindow;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JMenuBar {
    private final JMenu fileMenu = new JMenu("<html><font color='#d6d6d6'>File</font></html>");
    private final JMenu editMenu = new JMenu("<html><font color='#d6d6d6'>Edit</font></html>");
    private final JMenu helpMenu = new JMenu("<html><font color='#d6d6d6'>Help</font></html>");

    public MenuBar() {
        initFileMenu();
        initEditMenu();
        initHelpMenu();
        setBackground(Color.DARK_GRAY);
    }

    private void initFileMenu() {
        JMenuItem newTab = initNewTabItem();
        JMenuItem save = initSaveItem();
        JMenuItem load = initLoadItem();
        JMenuItem close = initCloseItem();
        JMenuItem exit = initExitItem();
        fileMenu.add(newTab);
        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(close);
        fileMenu.add(exit);
        this.add(fileMenu);
    }


    private JMenuItem initNewTabItem() {
        JMenuItem newTab = new JMenuItem("<html><font color='#d6d6d6'>New</font></html>");
        newTab.setBackground(Color.DARK_GRAY);
        newTab.addActionListener(actionEvent -> {
            MainWindow.tabBar.add("untiled" + MainWindow.tabsCount++, new DrawArea());
        });
        return newTab;
    }


    private JMenuItem initSaveItem() {
        JMenuItem save = new JMenuItem("<html><font color='#d6d6d6'>Save</font></html>");
        save.setBackground(Color.DARK_GRAY);

        return save;
    }

    private JMenuItem initLoadItem() {
        JMenuItem load = new JMenuItem("<html><font color='#d6d6d6'>Load</font></html>");
        load.setBackground(Color.darkGray);
        return load;
    }

    private JMenuItem initCloseItem() {
        JMenuItem close = new JMenuItem("<html><font color='#d6d6d6'>Close</font></html>");
        close.setBackground(Color.darkGray);
        close.addActionListener(actionEvent -> {
            MainWindow.tabBar.remove(MainWindow.tabBar.getSelectedComponent());
        });
        return close;
    }

    private JMenuItem initExitItem() {
        JMenuItem exit = new JMenuItem("<html><font color='#d6d6d6'>Exit</font></html>");
        exit.setBackground(Color.darkGray);
        exit.addActionListener(actionEvent -> {
            System.exit(0);
        });
        return exit;
    }

    private void initEditMenu() {
        this.add(editMenu);
    }

    private void initHelpMenu() {
        this.add(helpMenu);

    }

}
