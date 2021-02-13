package mainwindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ConfigurationBar extends JPanel {

    private final JButton colors = new JButton();

    private final JTextField thicknessFiled = new JTextField();
    private final JLabel thicknessLabel = new JLabel("<html><font color='#d6d6d6'>Thickness:</font></html>");
    public ConfigurationBar(){
        setBackground(Color.darkGray);
        initConfigurationBar();

    }

    private void initConfigurationBar(){
        BoxLayout temp = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(temp);

        initColorsButton();
        initThicknessField();

        add(new JToolBar.Separator());
        add(colors);
        add(new JToolBar.Separator());
        add(thicknessLabel);
        add(thicknessFiled);
        add(new JToolBar.Separator());

    }

    private ImageIcon initButtonIcon(String path) {
        Image dimg = null;
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            dimg = img.getScaledInstance(32,32, Image.SCALE_SMOOTH);
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
            //ToDo
//            MainWindow.currentInstrument = "colors";
        });
    }

    private void initThicknessField(){
        thicknessFiled.setText("1");
        thicknessFiled.setMaximumSize(new Dimension(110,32));
        thicknessFiled.setAlignmentX(1);
        thicknessLabel.setAlignmentX(1);
    }

}
