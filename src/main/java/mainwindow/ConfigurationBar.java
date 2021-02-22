package mainwindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ConfigurationBar extends JPanel {

    private final JButton colors = new JButton();

    private final JTextField thicknessField = new JTextField();
    private final JLabel thicknessLabel = new JLabel("<html><font color='#d6d6d6'>Thickness:</font></html>");

    private final JTextField zoomField = new JTextField();
    private final JLabel zoomLabel = new JLabel("<html><font color='#d6d6d6'>Zoom:</font></html>");

    public ConfigurationBar() {
        setBackground(Color.darkGray);
        initConfigurationBar();

    }

    private void initConfigurationBar() {
        BoxLayout temp = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(temp);

        initColorsButton();
        initThicknessField();
        initZoomField();

        add(new JToolBar.Separator());
        add(colors);
        add(new JToolBar.Separator());
        add(thicknessLabel);
        add(thicknessField);
        add(new JToolBar.Separator());
        add(zoomLabel);
        add(zoomField);
    }

    private ImageIcon initButtonIcon(String path) {
        Image dimg = null;
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            dimg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        assert dimg != null;
        return new ImageIcon(dimg);
    }

    private void initColorsButton() {
        colors.setIcon(initButtonIcon("/icons/color.png"));
        colors.setBackground(new Color(220, 220, 220));
        colors.setAlignmentX(1);
        colors.addActionListener(actionEvent -> {
            Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
            ((DrawArea) MainWindow.tabBar.getSelectedComponent()).setCurrentColor(newColor);
        });
    }

    private void initThicknessField() {
        thicknessField.setText("1");
        thicknessField.setMaximumSize(new Dimension(110, 32));
        thicknessField.setAlignmentX(1);
        thicknessLabel.setAlignmentX(1);
        thicknessField.getDocument().addDocumentListener(new ThicknessFieldListener());
    }

    private void initZoomField() {
        zoomField.setText("1");
        zoomField.setMaximumSize(new Dimension(110, 32));
        zoomField.setAlignmentX(1);
        zoomLabel.setAlignmentX(1);
        zoomField.getDocument().addDocumentListener(new ZoomFieldListener());
    }

    private class ThicknessFieldListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            setThickness();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            setThickness();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            setThickness();
        }

        private void setThickness() {
            int result = 1;
            try {
                result = Integer.parseInt(thicknessField.getText());
                thicknessField.setBackground(Color.white);
            } catch (NumberFormatException e) {
                thicknessField.setBackground(Color.red);
            }
            if (result > 0 && result < 200)
                ((DrawArea) MainWindow.tabBar.getSelectedComponent()).setThickness(result);
            else
                thicknessField.setBackground(Color.red);
        }
    }

    private class ZoomFieldListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            setZoom();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            setZoom();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            setZoom();
        }

        private void setZoom() {
            int result = 1;
            try {
                result = Integer.parseInt(zoomField.getText());
                zoomField.setBackground(Color.white);
            } catch (NumberFormatException e) {
                zoomField.setBackground(Color.red);
            }
            if (result > 0 && result < 10)
                ((DrawArea) MainWindow.tabBar.getSelectedComponent()).setZoom(result);
            else
                zoomField.setBackground(Color.red);

        }
    }
}
