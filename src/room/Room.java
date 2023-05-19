package room;

import entity.Player;
import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Room {

    public int room_type;
    public BufferedImage background;

    public BufferedImage prevArrow, nextArrow;
    public int mouseStatus = 0;

    public boolean bed_pressed = false;
    public boolean fridge_pressed = false;
    public boolean bathtub_pressed = false;

    private int frame = 0;

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
                if(x>620&&x<913&&y>320&&y<354){
                    room_type = 5; // egg_closeup
                }
                if(x>595&&x<917&&y>380&&y<450){
                    System.out.println("da");
                    Player.age = 2;
                    room_type = 1;
                }
                if(x>620&&x<913&&y>466&&y<491){
                    System.exit(0);
                }
            }
            else if(room_type == 4){ // football
                if(x>0&&x<100&&y>0&&y<100){ // press the top left corner to return in the house
                    room_type = 1; // bedroom
                }
                if(x>0&&x<100&&y>650&&y<1000 && GamePanel.getInstance().player.getAge() == 2){ // press the top left corner to return in the house
                    room_type = 8; // meteor mini game
                }
            }
            else if(room_type == 5){ // egg_closeup
                if(x>0&&x<3000&&y>0&&y<2000){ // on click anywhere
                    frame++;
                    if(frame > 3){
                        room_type = 7; // egg_at_home
                    }
                }
            }
            else if(room_type == 7){ // egg_at_home
                if(x>0&&x<3000&&y>0&&y<2000){ // press anywhere
                    frame++;
                    if(frame > 7){//to be corrected/checked
                        if(x>0&&x<700&&y>0&&y<2000) { // click on the left part of the screen
                            Player.gender = 0; // boy
                            room_type = 1; // bedroom
                        }
                        else{
                            Player.gender = 1; // girl
                            room_type = 1; // bedroom
                        }
                    }
                }
            }
            else if(room_type == 9){ // meteor_game_over
                if(x>630&&x<890&&y>315&&y<382){ // reset game / try again
                    GamePanel.getInstance().meteor1.reset();
                    room_type = 8; // meteor mini-game
                }
                else if(x>630&&x<890&&y>405&&y<450){
                    GamePanel.getInstance().meteor1.reset();
                    room_type = 1; // bedroom
                }
                //631; 295
                //887; 351

                //81; 384
                //887; 426
            }
            else if (room_type == 6){ // dead
                if(x>0&&x<3000&&y>0&&y<2000) { // anywhere on the screen
                    System.exit(0);
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

            if(room_type == 1){ // bedroom
                if(x>112&&x<387&&y>403&&y<715){ // click on the bed
                    bed_pressed = true;
                }
            }

            if(room_type == 2){ // kitchen
                if(x>1241&&x<1447&&y>108&&y<566){ // click on the fridge
                    fridge_pressed = true;
                }
            }

            if(room_type == 3){ // bathroom
                if(x>150&&x<604&&y>163&&y<576){ // click on the bathtub
                    bathtub_pressed = true;
                }
            }
        }

        if(room_type == 8){ // meteor mini-game
            if(GamePanel.getInstance().meteor1.getHits() > 2){
                GamePanel.getInstance().meteor1.reset();
                room_type = 9; // meteor_game_over
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
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/bedroom.png")));
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
                case 5: // egg_closeup
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/egg_closeup.png")));
                    break;
                case 6:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/dead.png")));
                    break;
                case 7:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/egg_at_home.png")));
                    break;
                case 8:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/meteor_shower.png")));
                    break;
                case 9:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/meteor_game_over.png")));
                    break;
                default:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/bedroom_simple.png")));
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
            g2.drawString("Load game", 680, 390);
            g2.drawString("Quit", 735, 460);
        }
        else if(room_type > 0 && room_type < 4){
            g2.drawImage(prevArrow, 22, 340, 15*6, 15*6, null);
            g2.drawImage(nextArrow, 1438, 340, 15*6, 15*6, null);
        }
        else if(room_type == 4){
            g2.drawImage(prevArrow, 10, 10, 15*6, 15*6, null);
            if(GamePanel.getInstance().player.getAge() == 2) {
                g2.drawImage(nextArrow, 10, 670, 15 * 6, 15 * 6, null);
            }
        }
        else if(room_type == 5){ // egg_closeup
            g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
            g2.setColor(Color.white);
            g2.drawString("Click anywhere to continue...", 1000, 700);
            if(frame == 1) {
                g2.drawString("Wow! Such a colorful... thing!", 500, 100);
            }
            if(frame == 2) {
                g2.drawString("I must have it!", 500, 100);
            }
            if(frame == 3) {
                g2.drawString("I'll bring it back home :) !", 500, 100);
            }
        }
        else if(room_type == 7){ // egg_at_home
            g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
            g2.setColor(Color.white);
            g2.drawString("Click anywhere to continue...", 1000, 700);
            if(frame == 4) {
                g2.drawString("You can sit on my bean bag, special thing!", 500, 100);
            }
            if(frame == 5) {
                g2.drawString("ker-ack! kerr...ker-ack!!", 500, 100);
            }
            if(frame == 6) {
                g2.drawString("What on God's green Earth?!", 500, 100);
            }
            if(frame == 7) {
                g2.drawString("It's a...", 1000, 100);
                g2.setColor(Color.cyan);
                g2.drawString("TamaSaurier", 300, 400);
                g2.setColor(Color.pink);
                g2.drawString("TamaSaurierin", 1000, 400);
            }
        }
        else if(room_type == 9){
            g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
            g2.setColor(Color.white);
            g2.drawString("Try again", 700, 333);
            g2.drawString(" Give up", 700, 410);
            g2.drawString(" Score: " + GamePanel.getInstance().meteor1.getScore(), 700, 490);
        }

    }

}
