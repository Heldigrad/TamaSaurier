package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Meteor extends Entity{

    public static int score = 0;
    public static int hits = 0;
    public boolean intersects_player = false;
    public int type;

    public Meteor(int type){
        this.type = type;
        setMeteorImage(type);
        setDefaultValues();
    }

    public void setDefaultValues(){
        this.x = (int)(Math.random() * (1500 - width) + 0);
        this.y = -height;
        this.speed = 4;
    }

    public void reset(){
        score = 0;
        hits = 0;
    }

    public void intersects_player(int cx, int cy) {
        GamePanel gp = GamePanel.getInstance();

        //top part of the meteor
        if(cx > this.x - gp.player.width * 6 && cx < this.x + this.width * 6){
            if(cy > this.y - gp.player.height * 6 && cy < this.y + this.height * 6 - gp.player.height * 6){
                intersects_player = true;
            }
        }

        // bottom part of the meteor
        if(cx > this.x-gp.player.width * 6 && cx < this.x + this.width * 6){
            if(cy > this.y && cy < this.y + this.height * 6){
                intersects_player = true;
            }
        }

        // left part of the meteor
        if(cx > this.x - gp.player.width * 6 && cx < this.x + this.width * 6 - gp.player.width * 6){
            if(cy > this.y - gp.player.height * 6 && cy < this.y + this.height * 6){
                intersects_player = true;
            }
        }

        // right part of the meteor
        if(cx > this.x && cx < this.x + this.width * 4){
            if(cy > this.y - gp.player.height * 6 && cy < this.y + this.height * 6){
                intersects_player = true;
            }
        }
    }


    public void setMeteorImage(int type){
        this.type = type;
        try{
            switch (type) {
                case 0 -> { // meteor_1_
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_1_.png")));
                    this.width = 18;
                    this.height = 30;
                }
                case 1 -> { // meteor_1_mirr
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_1_mirr.png")));
                    this.width = 18;
                    this.height = 30;
                }
                case 2 -> { // meteor_2_
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_2_.png")));
                    this.width = 17;
                    this.height = 30;
                }
                case 3 -> { // meteor_2_mirr
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_2_mirr.png")));
                    this.width = 17;
                    this.height = 30;
                }
                case 4 -> { // meteor_3_
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_3_.png")));
                    this.width = 22;
                    this.height = 34;
                }
                case 5 -> { // meteor_3_mirr
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_3_mirr.png")));
                    this.width = 22;
                    this.height = 34;
                }
                case 6 -> { // meteor_4_
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_4_.png")));
                    this.width = 28;
                    this.height = 28;
                }
                case 7 -> { // meteor_4_mirr
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_4_mirr.png")));
                    this.width = 28;
                    this.height = 28;
                }
                default ->
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/meteor_1_.png")));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    void setX(int x){
        this.x = x;
    }

    public int getScore(){
        return score;
    }

    public int getHits(){
        return hits;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public void update(){
        intersects_player(GamePanel.getInstance().player.x, GamePanel.getInstance().player.y);
        this.y += speed;

        if(intersects_player){
            hits++;
            intersects_player = false;
            setX((int) (Math.random() * (1500 - width) + 0));
            setMeteorImage((int) (Math.random() * (7) + 0));
            this.y  = -height*6;
        }

        if(this.y > 1000) {
            score++;
            if(score < 10){
                setSpeed((int) (Math.random() * (7 - 2) + 2));
            }
            else if(score < 30){
                setSpeed((int) (Math.random() * (12 - 8) + 8));
            }
            else{
                setSpeed((int) (Math.random() * (17 - 12) + 12));
            }

            setX((int) (Math.random() * (1500 - width) + 0));
            setMeteorImage((int) (Math.random() * (7) + 0));
            this.y  = -height*6;
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image,  this.x, this.y, width * 6, height * 6, null);
    }
}
