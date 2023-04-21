package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Goal extends Entity{
    GamePanel gp;

    public Goal(GamePanel gp){
        this.gp = gp;
        this.x = 565;
        this.y = 0;
        this.width = 44;
        this.height = 18;
        this.speed = 0;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/goal_done.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, width * 9, height * 9, null);
    }
}
