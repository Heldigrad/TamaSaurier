package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage(){
        try{

            dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update(){
        if(keyH.upPressed){
            y -= speed;
        }
        else if (keyH.downPressed){
            y += speed;
        }
        else if (keyH.leftPressed){
            x -= speed;
        }
        else if (keyH.rightPressed){
            x += speed;
        }
    }

    public void draw(Graphics2D g2){

        g2.drawImage(dino, x, y, gp.tileSize*6, gp.tileSize*6, null);
    }
}
