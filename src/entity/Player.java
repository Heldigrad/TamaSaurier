package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Player extends Entity{
    GamePanel gp;

    boolean is_moving = false;
    public int next_x = 0, next_y = 0;

    public static int age = 0;

    public Player(GamePanel gp){
        this.gp = gp;
        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage(){
        try{
            if(age == 0){
                if(x < next_x) {
                    dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/baby_girl_mirr.png"));
                }
                else {
                    dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/baby_girl.png"));
                }
            }

            if(age == 1){
                if(x < next_x) {
                    dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/teen_girl_mirr.png"));
                }
                else {
                    dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/teen_girl.png"));
                }
            }

            if(age >= 2){
                if(x < next_x) {
                    dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl_mirr.png"));
                }
                else {
                    dino = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl.png"));
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(){
        x = 675;
        y = 380;

        speed = 4;
    }

    public void update(){
        if(!is_moving) {
            int move = (int) (Math.random() * (300 - 0)) + 0;// probabilitatea sa se mute
            if(move == 7){
                is_moving = true;

                next_x = (int) (Math.random() * (1000 - 375)) + 375;
                next_y = (int) (Math.random() * (460 - 300)) + 300;

                next_x -= next_x % speed;
                next_y -= next_y % speed;

                getPlayerImage();
            }
        }
        else{// is_moving
            int new_speed = (int) (Math.random() * (2 * speed - speed)) + speed;

            if(x == next_x && y == next_y){
                is_moving = false;
            }

            if(Math.abs(x - next_x) <= speed){
                x = next_x;
            }
            else if(x < next_x){
                x += new_speed;
            }
            else if(x > next_x){
                x -= new_speed;
            }

            if(Math.abs(y - next_y) <= speed){
                y = next_y;
            }
            else if(y < next_y){
                y += new_speed;
            }
            else if(y > next_y) {
                y -= new_speed;
            }

        }

    }

    public void draw(Graphics2D g2){
        g2.drawImage(dino, x, y, 33*6, 45*6, null);
    }
}
