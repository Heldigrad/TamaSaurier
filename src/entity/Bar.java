package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bar{
    public float level;
    public BufferedImage bar;

    Bar(String imgSrc) { // constructor
        try {
            bar = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imgSrc)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2, Color color, int x, int y) {
        g2.setColor(color);

        g2.drawRoundRect(x, y, (int) (level>1 ? 18*8 : 19*8.5 * level), 35, 50, 50);
        g2.fillRoundRect(x, y, (int) (level>1 ? 18*8 : 19*8.5 * level), 35, 50, 50);
        g2.drawImage(bar, x-35, y-28, 30*8, 10*8, null);
    }
}