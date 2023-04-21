package entity;

import main.GamePanel;
import main.KeyHandler;
import room.Room;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Objects;
import java.util.Random;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    boolean is_moving = false;
    public int next_x = 0, next_y = 0;

    public static int age = 0;

    public Player(GamePanel gp){
        this.gp = gp;
        this.width = 13;
        this.height = 11;
        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage(){
        try{
            if(age == 0){
                this.width = 13;
                this.height = 11;
                if(x < next_x) {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/baby_girl_mirr.png")));
                }
                else {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/baby_girl.png")));
                }
            }

            if(age == 1){
                this.width = 21;
                this.height = 24;
                if(x < next_x) {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/teen_girl_mirr.png")));
                }
                else {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/teen_girl.png")));
                }
            }

            if(age >= 2){
                this.width = 32;
                this.height = 38;
                this.speed = 6;
                if(x < next_x) {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl_mirr.png")));
                }
                else {
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl.png")));
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

    public boolean intersects_wall(int cx, int cy){
        if(cy <= 0){ // top
            return true;
        }
        if(cy + this.height * 6 >= 765){ // bottom
            return true;
        }
        if(cx <= 0){ // left
            return true;
        }
        if(cx + this.width * 6 >= 1535 ){ // right
            return true;
        }
        return false;
    }

    public boolean intersects_goal(int cx, int cy){
        // top part of the goal
        if(cx > gp.goal.x - this.width * 6 && cx < gp.goal.x + gp.goal.width * 9){
            if(cy > gp.goal.y - this.height * 6 && cy < gp.goal.y + gp.goal.height * 9 - this.height * 6){
                return true;
            }
        }

        // bottom part of the goal
        if(cx > gp.goal.x - this.width * 6 && cx < gp.goal.x + gp.goal.width * 9){
            if(cy > gp.goal.y && cy < gp.goal.y + gp.goal.height * 9){
                return true;
            }
        }

        // left part of the goal
        if(cx > gp.goal.x - this.width * 6 && cx < gp.goal.x + gp.goal.width * 9 - this.width * 6){
            if(cy > gp.goal.y - this.height * 6 && cy < gp.goal.y + gp.goal.height * 9){
                return true;
            }
        }

        // right part of the goal
        if(cx > gp.goal.x && cx < gp.goal.x + gp.goal.width * 9){
            if(cy > gp.goal.y - this.height * 6 && cy < gp.goal.y + gp.goal.height * 9){
                return true;
            }
        }

        return false;
    }

    public void update(){
        if(gp.room.room_type == 4){
            if(gp.keyH.upPressed && !(this.intersects_wall(this.x, this.y - speed) || gp.football.intersects_player(this.x, this.y - speed) || this.intersects_goal(this.x, this.y - speed))){
                this.y -= speed;
            }
            if(gp.keyH.downPressed && !(this.intersects_wall(this.x, this.y + speed) || gp.football.intersects_player(this.x, this.y + speed) || this.intersects_goal(this.x, this.y + speed))){
                y += speed;
            }
            if(gp.keyH.leftPressed && !(this.intersects_wall(this.x - speed, this.y) || gp.football.intersects_player(this.x - speed, this.y) || this.intersects_goal(this.x - speed, this.y))){
                x -= speed;
                next_x = x - 1;
            }
            if(gp.keyH.rightPressed && !(this.intersects_wall(this.x + speed, this.y) || gp.football.intersects_player(this.x + speed, this.y) || this.intersects_goal(this.x + speed, this.y))){
                x += speed;
                next_x = x + 1;
            }
        }
        else{
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
        getPlayerImage();
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, width*6, height*6, null);
    }
}
