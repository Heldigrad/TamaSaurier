package room;

import main.KeyHandler;
import main.MouseHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class Room {

    public int room_type;
    public BufferedImage background;

    public BufferedImage prevArrow, nextArrow;
    public int mouseStatus = 0;

    KeyHandler keyH;
    public Room(int type, KeyHandler keyH){
        this.room_type = type;
        this.keyH = keyH;
        getRoomImage();
    }

    public void update(){
        if(MouseHandler.clicked && mouseStatus == 0){
            mouseStatus = 1;
        }
        else if(MouseHandler.clicked && mouseStatus == 1){
            mouseStatus = 2;
        }
        else if(!MouseHandler.clicked){
            mouseStatus = 0;
        }

        if(mouseStatus == 1){
            var x = MouseHandler.pos.getX();
            var y = MouseHandler.pos.getY();

            if(room_type == 0){ // menu
                if(x>622&&x<913&&y>320&&y<354){
                    room_type = 1;
                }
                if(x>629&&x<914&&y>466&&y<491){
                    System.exit(0);
                }
            }
            else if(room_type == 4){
                if(x>0&&x<100&&y>0&&y<100){ // press the top left corner to return in the house
                    room_type = 1;
                }
            }
            else{ // any other room
                if(x>48&&x<106&&y>379&&y<451){ // prev_pressed
                    if(room_type == 1){
                        room_type = 4;
                    }
                    else {
                        room_type--;
                    }
                }

                if(x>1459&&x<1521&&y>382&&y<452){ // next_pressed
                    if(room_type == 4){
                        room_type = 1;
                    }
                    else {
                        room_type++;
                    }
                }
            }
        }

        if(keyH.escPressed){
            room_type = 0;
        }

        getRoomImage();
    }

    public void getRoomImage(){
        try{
            prevArrow = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/arrow_mirr.png")));
            nextArrow = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/arrow.png")));
            switch (room_type) {
                case 0: // menu
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/menu.png")));
                    break;
                case 1: // bedroom
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/bedroom_simple.png")));
                    break;
                case 2: // kitchen
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/kitchen.png")));
                    break;
                case 3: // bathroom
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/bathroom_done.png")));
                    break;
                case 4: // football field
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/football_field.png")));
                    break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(background, 0, 0, 16*3*32, 16*3*16, null);

        if(room_type == 0){
            g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
            g2.setColor(Color.white);
            g2.drawString("Start", 730, 321);
            g2.drawString("Options", 710, 390);
            g2.drawString("Quit", 735, 460);
        }
        if(room_type > 0 && room_type < 4){
            g2.drawImage(prevArrow, 22, 340, 15*6, 15*6, null);
            g2.drawImage(nextArrow, 1438, 340, 15*6, 15*6, null);
        }
    }

}
