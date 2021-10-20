package shapes;

import java.awt.*;

public class Picture extends Shape {
    private final Image image;

    public Picture(Image image) {
        this.image = image;
    }

    public Picture(int startX, int startY, Image image) {
        this.startX = startX;
        this.startY = startY;
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, startX, startY, null);
    }
}
