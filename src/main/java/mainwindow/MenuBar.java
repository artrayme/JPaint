package mainwindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
            MainWindow.tabBar.add("tab" + MainWindow.tabsCount++,(new DrawArea()));
        });
        return newTab;
    }


    private JMenuItem initSaveItem() {
        JMenuItem save = new JMenuItem("<html><font color='#d6d6d6'>Save</font></html>");
        save.setBackground(Color.DARK_GRAY);
        save.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).savePicture(selectedFile);
            }
        });
        return save;
    }

    private JMenuItem initLoadItem() {
        JMenuItem load = new JMenuItem("<html><font color='#d6d6d6'>Load</font></html>");
        load.setBackground(Color.darkGray);
        load.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).loadPicture(selectedFile);

            }
        });
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
        JMenuItem pencil = initPencilInstrumentItem();
        JMenuItem line = initLineInstrumentItem();
        JMenuItem rectangle = initRectangleInstrumentItem();
        JMenuItem ellipse = initEllipseInstrumentItem();
        JMenuItem text = initTextInstrumentItem();
        JMenuItem selectionRectangle = initSelectionRectangleInstrumentItem();
        JMenuItem lasso = initLassoInstrumentItem();
        JMenuItem zoom = initZoomInstrumentItem();
        editMenu.add(pencil);
        editMenu.add(line);
        editMenu.add(rectangle);
        editMenu.add(ellipse);
        editMenu.add(text);
        editMenu.add(selectionRectangle);
        editMenu.add(lasso);
        editMenu.add(zoom);
        this.add(editMenu);
    }

    private JMenuItem initPencilInstrumentItem() {
        JMenuItem pencil = new JMenuItem("<html><font color='#d6d6d6'>Pencil</font></html>");
        pencil.setBackground(Color.DARK_GRAY);
        pencil.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).pencil();
        });
        return pencil;
    }


    private JMenuItem initLineInstrumentItem() {
        JMenuItem line = new JMenuItem("<html><font color='#d6d6d6'>Line</font></html>");
        line.setBackground(Color.DARK_GRAY);
        line.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).line();
        });
        return line;
    }

    private JMenuItem initRectangleInstrumentItem() {
        JMenuItem rectangle = new JMenuItem("<html><font color='#d6d6d6'>Rectangle</font></html>");
        rectangle.setBackground(Color.darkGray);
        rectangle.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).rectangle();

        });
        return rectangle;
    }

    private JMenuItem initEllipseInstrumentItem() {
        JMenuItem ellipse = new JMenuItem("<html><font color='#d6d6d6'>Ellipse</font></html>");
        ellipse.setBackground(Color.darkGray);
        ellipse.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).ellipse();
        });
        return ellipse;
    }

    private JMenuItem initTextInstrumentItem() {
        JMenuItem text = new JMenuItem("<html><font color='#d6d6d6'>Text</font></html>");
        text.setBackground(Color.darkGray);
        text.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).text();
        });
        return text;
    }

    private JMenuItem initSelectionRectangleInstrumentItem() {
        JMenuItem selectionRectangle = new JMenuItem("<html><font color='#d6d6d6'>Select</font></html>");
        selectionRectangle.setBackground(Color.darkGray);
        selectionRectangle.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).selection();
        });
        return selectionRectangle;
    }

    private JMenuItem initLassoInstrumentItem() {
        JMenuItem lasso = new JMenuItem("<html><font color='#d6d6d6'>Lasso</font></html>");
        lasso.setBackground(Color.darkGray);
        lasso.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).lasso();
        });
        return lasso;
    }

    private JMenuItem initZoomInstrumentItem() {
        JMenuItem zoom = new JMenuItem("<html><font color='#d6d6d6'>Zoom</font></html>");
        zoom.setBackground(Color.darkGray);
        zoom.addActionListener(actionEvent -> {
            (((DrawArea)((JScrollPane)MainWindow.tabBar.getSelectedComponent()).getViewport().getView())).zoom();
        });
        return zoom;
    }

    private void initHelpMenu() {
        JMenuItem about = initAboutItem();
        helpMenu.add(about);
        this.add(helpMenu);
    }

    private JMenuItem initAboutItem() {
        JMenuItem about = new JMenuItem("<html><font color='#d6d6d6'>About</font></html>");
        about.setBackground(Color.darkGray);
        about.addActionListener(actionEvent -> {
            new AboutWindow(this.getX(), this.getY());
        });
        return about;
    }

}
