package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Football extends Entity{

    GamePanel gp;
    public int score;

    public Football(GamePanel gp){
        this.gp = gp;
        this.x = 700;
        this.y = 500;
        this.width = 17;
        this.height = 17;
        this.speed = 4;
        this.score = 0;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/football.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(){
        this.x = 700;
        this.y = 500;
    }

    public boolean intersects_wall(int cx, int cy){
        if(cy < 0){ // top
            return true;
        }
        if(cy + this.height * 4 > 765){ // bottom
            return true;
        }
        if(cx < 0){ // left
            return true;
        }
        if(cx + this.width * 4 > 1535){ // right
            return true;
        }
        return false;
    }

    public boolean scored(int cx, int cy){
        // 708, 88 // 565, 893, 18
        if(cx > gp.goal.x && cx < gp.goal.x + gp.goal.width * 9 - this.width * 4){
            if(cy <= gp.goal.y + gp.goal.height * 9 + 32){
                System.out.println("GOAL");
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean intersects_player(int cx, int cy){
        // top part of the ball
        if(cx > this.x - gp.player.width * 6 && cx < this.x + this.width * 4){
            if(cy > this.y - gp.player.height * 6 && cy < this.y + this.height * 4 - gp.player.height * 6){
                return true;
            }
        }

        // bottom part of the ball
        if(cx > this.x-gp.player.width * 6 && cx < this.x + this.width * 4){
            if(cy > this.y && cy < this.y + this.height * 4){
                return true;
            }
        }

        // left part of the ball
        if(cx > this.x - gp.player.width * 6 && cx < this.x + this.width * 4 - gp.player.width * 6){
            if(cy > this.y - gp.player.height * 6 && cy < this.y + this.height * 4){
                return true;
            }
        }

        // right part of the ball
        if(cx > this.x && cx < this.x + this.width * 4){
            if(cy > this.y - gp.player.height * 6 && cy < this.y + this.height * 4){
                return true;
            }
        }

        return false;
    }

    public void update(){
        if(intersects_player(gp.player.x, gp.player.y - gp.player.speed) && !intersects_wall(this.x, this.y - this.speed)){ // top
            this.y -= speed;
        }
        if(intersects_player(gp.player.x, gp.player.y + gp.player.speed) && !intersects_wall(this.x, this.y + this.speed)){ // bottom
            this.y += speed;
        }
        if(intersects_player(gp.player.x - gp.player.speed, gp.player.y) && !intersects_wall(this.x - this.speed, this.y)){ // left
            this.x -= speed;
        }
        if(intersects_player(gp.player.x + gp.player.speed, gp.player.y) && !intersects_wall(this.x - this.speed, this.y)){ // right
            this.x += speed;
        }
        if(scored(this.x, this.y)){
            setDefaultValues();
            this.score++;
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, width * 4, height * 4, null);
    }
}
