package room;

import entity.Card;
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
                if(x>620&&x<913&&y>320&&y<354){ // New Game
                    Player.getInstance().set_start_values();
                    room_type = 5; // egg_closeup
                }
                if(x>595&&x<917&&y>380&&y<450){ // Load Game
                    room_type = 1;
                }
                if(x>620&&x<913&&y>466&&y<491){ // Exit
                    System.exit(0);
                }
            }

            else if(room_type == 4){ // football
                if(x>0&&x<100&&y>0&&y<100){ // back arrow
                    room_type = 1; // bedroom
                }
                if(x>0&&x<100&&y>650&&y<1000 && GamePanel.getInstance().player.getAge() > 0){ // next arrow, botton left
                    room_type = 11; // meteor mini game
                }
                if(x>1400&&x<2000&&y>672&&y<1000 && GamePanel.getInstance().player.getAge() > 1){ // next arrow, bottom right
                    room_type = 8; // meteor mini game
                }
            }

            if(room_type == 5){ // egg_closeup
                if(x>0&&x<3000&&y>0&&y<2000){ // on click anywhere
                    frame++;
                    if(frame > 3){
                        room_type = 7; // egg_at_home
                    }
                }
            }
            if(room_type == 7){ // egg_at_home
                if(x>0&&x<3000&&y>0&&y<2000){ // press anywhere
                    frame++;
                    if(frame > 7){//to be corrected/checked
                        if(x>0&&x<700&&y>0&&y<2000) { // click on the left part of the screen
                            Player.getInstance().gender = 0; // boy
                            room_type = 1; // bedroom
                        }
                        else{
                            Player.getInstance().gender = 1; // girl
                            room_type = 1; // bedroom
                        }
                    }
                }
            }
            if(room_type == 8){ // meteor mini-game
                GamePanel.getInstance().player.fun.level = 1;
                if(x>0&&x<100&&y>0&&y<100){ // reset game / try again
                    GamePanel.getInstance().meteor1.reset();
                    room_type = 1; // meteor mini-game
                }
            }

            if(room_type == 9){ // meteor_game_over
                GamePanel.getInstance().player.fun.level = 1;
                if(x>630&&x<890&&y>315&&y<382){ // reset game / try again
                    GamePanel.getInstance().meteor1.reset();
                    room_type = 8; // meteor mini-game
                }
                else if(x>630&&x<890&&y>405&&y<450){
                    room_type = 1; // bedroom
                }
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
                if(!GamePanel.getInstance().start_game){
                    GamePanel.getInstance().start_game = true;
                }
                if(x>112&&x<387&&y>403&&y<715){ // click on the bed
                    GamePanel.getInstance().player.x = 335-GamePanel.getInstance().player.width * 6;
                    GamePanel.getInstance().player.y = 580-GamePanel.getInstance().player.height * 6;
                    bed_pressed = true;
                }
            }

            else if(room_type == 2){ // kitchen
                if(x>1241&&x<1447&&y>108&&y<566){ // click on the fridge
                    room_type = 10; // open_fridge
                }
            }

            else if(room_type == 3){ // bathroom
                if(x>150&&x<604&&y>163&&y<576){ // click on the bathtub
                    GamePanel.getInstance().player.x = 413-GamePanel.getInstance().player.width * 6;
                    GamePanel.getInstance().player.y = 432-GamePanel.getInstance().player.height * 6;
                    bathtub_pressed = true;
                }
            }

            if(room_type == 10){ // open_fridge
                if(Player.getInstance().hunger.level < 1) {
                    if (x > 416 && x < 545 && y > 182 && y < 295) { // food (1, 1)
                        if (GamePanel.getInstance().food1.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food1.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food1);
                            GamePanel.getInstance().food1.visible = false;
                        }
                    }
                    if (x > 674 && x < 806 && y > 182 && y < 295) { // food (1, 2)
                        if (GamePanel.getInstance().food2.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food2.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food2);
                            GamePanel.getInstance().food2.visible = false;
                        }
                    }
                    if (x > 956 && x < 1102 && y > 182 && y < 295) { // food (1, 3)
                        if (GamePanel.getInstance().food3.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food3.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food3);
                            GamePanel.getInstance().food3.visible = false;
                        }
                    }
                    if (x > 416 && x < 545 && y > 387 && y < 517) { // food (2, 1)
                        if (GamePanel.getInstance().food4.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food4.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food4);
                            GamePanel.getInstance().food3.visible = false;
                        }
                    }
                    if (x > 674 && x < 806 && y > 387 && y < 517) { // food (2, 2)
                        if (GamePanel.getInstance().food5.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food5.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food5);
                            GamePanel.getInstance().food5.visible = false;
                        }
                    }
                    if (x > 956 && x < 1102 && y > 387 && y < 517) { // food (2, 3)
                        if (GamePanel.getInstance().food6.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food6.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food6);
                            GamePanel.getInstance().food6.visible = false;
                        }
                    }
                    if (x > 416 && x < 545 && y > 600 && y < 720) { // food (3, 1)
                        if (GamePanel.getInstance().food7.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food7.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food7);
                            GamePanel.getInstance().food7.visible = false;
                        }
                    }
                    if (x > 674 && x < 806 && y > 600 && y < 720) { // food (3, 2)
                        if (GamePanel.getInstance().food8.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food8.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food8);
                            GamePanel.getInstance().food8.visible = false;
                        }
                    }
                    if (x > 956 && x < 1102 && y > 600 && y < 720) { // food (3, 3)
                        if (GamePanel.getInstance().food9.visible) {
                            Player.getInstance().hunger.level += (float) 1 / 100 * GamePanel.getInstance().food9.saturation;
                            Player.getInstance().setAffinities(GamePanel.getInstance().food9);
                            GamePanel.getInstance().food9.visible = false;
                        }
                    }
                }
                if (x > 0 && x < 391 && y > 122 && y < 755) { // fridge door
                    room_type = 2; // kitchen
                }
            }

            if(room_type == 11){ // card game
                GamePanel.getInstance().player.fun.level = 1;
                if (!GamePanel.getInstance().wait) {
                    Card.card_click(GamePanel.getInstance().cards, (int) x, (int) y);
                }

                if (x > 0 && x < 199 && y > 0 && y < 100) { // top left part of the screen
                    room_type = 4; //
                }
                if(GamePanel.getInstance().done && GamePanel.getInstance().stage == 4){
                    Card.resize(1);
                }
                else if(GamePanel.getInstance().done && GamePanel.getInstance().stage == 6){
                    Card.resize(2);
                }
            }

            if(room_type == 12){ // card_game_over
                GamePanel.getInstance().player.fun.level = 1;
                GamePanel.getInstance().started = false;
                Card.resize(0);
                if (x > 590 && x < 895 && y > 320 && y < 385) { // Try again
                    room_type = 11; // card mini-game
                    GamePanel.getInstance().stage = 0;
                }
                if (x > 590 && x < 895 && y > 395 && y < 455) { // Give up
                    room_type = 1; // bedroom
                }
            }
        }

        if(room_type == 8){ // meteor mini-game
            if(GamePanel.getInstance().meteor1.getHits() > 2){
                room_type = 9; // meteor_game_over
            }
        }

        if(room_type == 11){ // card game
            if(!GamePanel.getInstance().started){
                GamePanel.getInstance().timer = System.nanoTime();
                GamePanel.getInstance().started = true;
            }
            if(GamePanel.getInstance().timer + 60L * 1000000000 < System.nanoTime()){
                room_type = 12; // game_over
                GamePanel.getInstance().started = false;
            }
            if (GamePanel.getInstance().wait) {
                var timeDif = System.nanoTime() - GamePanel.getInstance().waitStart;

                if (timeDif >  500000000) {
                    GamePanel.getInstance().wait = false;
                    if(GamePanel.getInstance().done){
                        Card.shuffle_matrix(GamePanel.getInstance().cards);
                        GamePanel.getInstance().done = false;
                    }
                    for (int i = 0; i < GamePanel.getInstance().cards.length; ++i) {
                        for(int j = 0; j < GamePanel.getInstance().cards[0].length; ++j) {
                            GamePanel.getInstance().cards[i][j].visible = false;
                            GamePanel.getInstance().cards[i][j].setImage(GamePanel.getInstance().cards[i][j].type);
                        }
                    }
                    GamePanel.getInstance().visibleCount = 0;
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
                case 10:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/open_fridge.png")));
                    break;
                case 11:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/card_game.png")));
                    break;
                case 12:
                    background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("rooms/card_game_over.png")));
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
            g2.drawString("New game", 685, 321);
            g2.drawString("Load game", 680, 390);
            g2.drawString("Quit", 735, 460);
        }
        else if(room_type > 0 && room_type < 4){
            g2.drawImage(prevArrow, 22, 340, 15*6, 15*6, null);
            g2.drawImage(nextArrow, 1438, 340, 15*6, 15*6, null);
        }
        else if(room_type == 4){
            g2.drawImage(prevArrow, 10, 10, 15*6, 15*6, null);
            if(GamePanel.getInstance().player.getAge() > 1) {
                g2.drawImage(nextArrow, 1410, 670, 15 * 6, 15 * 6, null);
            }
            if(GamePanel.getInstance().player.getAge() > 0) {
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
            g2.drawString("Try again", 680, 333);
            g2.drawString(" Give up", 680, 410);
            g2.drawString(" Score: " + GamePanel.getInstance().meteor1.getScore(), 680, 490);
            if(GamePanel.getInstance().meteor1.getScore() >= GamePanel.getInstance().high_score_meteor){
                g2.drawString("New High Score!", 625, 555);
                GamePanel.getInstance().high_score_meteor = GamePanel.getInstance().meteor1.getScore();
            }
            g2.drawString("High Score: " + GamePanel.getInstance().high_score_meteor, 650, 520);
        }
        else if(room_type == 12){
            g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
            g2.setColor(Color.magenta);
            if(GamePanel.getInstance().stage >= GamePanel.getInstance().high_score_card){
                GamePanel.getInstance().high_score_card = GamePanel.getInstance().stage;
                g2.drawString(" New High score!", 615, 560);
            }
            g2.setColor(Color.white);
            g2.drawString("Try again", 675, 363);
            g2.drawString(" Give up", 675, 440);
            g2.setColor(Color.magenta);
            g2.drawString("You reached: stage " + GamePanel.getInstance().stage, 582, 495);
            g2.drawString("High score: " + GamePanel.getInstance().high_score_card, 650, 527);
        }
    }

}
