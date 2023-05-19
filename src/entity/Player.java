package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    //dinosaur characteristics:
    private static Player instance;
    public static int age = 0;
    public static int gender = 1;
    public Bar energy = new Bar("stuff/energy_bar_empty.png");
    public Bar hunger = new Bar("stuff/food_bar.png");
    public Bar fun = new Bar("stuff/fun_bar.png");
    public Bar hygiene = new Bar("stuff/hygiene_bar.png");

    //dinosaur movement:
    boolean is_moving = false;
    public int next_x = 0, next_y = 0;

    //constructor:
    private Player(){
        this.width = 13;
        this.height = 11;
        setDefaultValues();
        getPlayerImage();
    }

    //player instance
    public static Player getInstance(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }

    //function that checks death conditions
    public void dead(){
        if(energy.level <= 0 && hunger.level <= 0){
            GamePanel.getInstance().room.room_type = 6;
        }
    }

    //function for choosing the correct dinosaur skin
    public void getPlayerImage(){
        try{
            if(gender == 1) {
                if (age == 0) {
                    this.width = 13;
                    this.height = 11;
                    if (x < next_x) {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/baby_girl_mirr.png")));
                    } else {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/baby_girl.png")));
                    }
                }

                if (age == 1) {
                    this.width = 21;
                    this.height = 24;
                    if (x < next_x) {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/teen_girl_mirr.png")));
                    } else {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/teen_girl.png")));
                    }
                }

                if (age >= 2) {
                    this.width = 32;
                    this.height = 38;
                    if (x < next_x) {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl_mirr.png")));
                    } else {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/adult_girl.png")));
                    }
                }
            }
            else{
                if (age == 0) {
                    this.width = 13;
                    this.height = 11;
                    if (x < next_x) {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/baby_boy_mirr.png")));
                    } else {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/baby_boy.png")));
                    }
                }

                if (age == 1) {
                    this.width = 20;
                    this.height = 24;
                    if(gender == 0) { // girl
                        this.width = 21;
                    }
                    if (x < next_x) {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/teen_boy_mirr.png")));
                    } else {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/teen_boy.png")));
                    }
                }

                if (age >= 2) {
                    this.width = 32;
                    this.height = 38;
                    if (x < next_x) {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/adult_boy_mirr.png")));
                    } else {
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("dinos/adult_boy.png")));
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //default player position
    public void setDefaultValues(){
        x = 733;
        y = 680 - this.height;
        speed = 4;
    }

    //function that checks if the dinosaur intersects room margins
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

    //function that checks if the dinosaur intersects the goal in the football mini game
    public boolean intersects_goal(int cx, int cy){
        GamePanel gp = GamePanel.getInstance();

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

    //function that updates player position
    public void update(){
        GamePanel gp = GamePanel.getInstance();

        if(energy.level > -1){
            energy.level -= 0.0001;
        }
        if(hunger.level > -1){
            hunger.level -= 0.0002;
        }
        if(fun.level > -1){
            fun.level -= 0.0003;
        }
        if(hygiene.level > -1){
            hygiene.level -= 0.0001;
        }

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
                int move = (int) (Math.random() * (300 - 0)) + 0;// movement probability
                if(move == 7){
                    is_moving = true;

                    next_x = (int) (Math.random() * ((1090 - this.width * 6) - 384 )) + 384;
                    next_y = (int) (Math.random() * ((750 - this.height * 6) - (540 - this.height * 6))) + (540 - this.height * 6);

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
        dead();
    }

    //function that draws the player on the screen
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, width*6, height*6, null);
    }
}
