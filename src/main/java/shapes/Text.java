package shapes;

import java.awt.*;

public class Text extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawString(text, startX, startY);
    }
}
