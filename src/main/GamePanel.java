package main;

import entity.Football;
import entity.Goal;
import entity.Meteor;
import entity.Player;
import room.Room;

import javax.swing.*;
import java.awt.*;

//this game panel works as a kind of game screen
public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int originalTileSize = 16; // 16x16 tiles - MIGHT CHANGE
    final int scale = 3; // this will make the character appear a bit bigger -> visible

    public final int tileSize = originalTileSize * scale; // actual tile size
    final int maxScreenCol = 32;
    final int maxScreenRow = 16;
    //=> the screen size will be 4:3 <=> 16x12
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    //=> actual window size in pixels

    public static long startTime = 0;

    //FPS
    int FPS = 60;

    public KeyHandler keyH = new KeyHandler();
    Thread gameThread; // once a thread is started, it keeps the program running until you stop it
    //when it's started, the RUN method found below is automatically called

    //room and player
    public Room room = new Room(0, keyH);
    public Player player = Player.getInstance();

    //football mini-game
    public Football football = new Football(this);
    public Goal goal = new Goal(this);

    //meteor mini-game
    public Meteor meteor1 = new Meteor(0);
    public Meteor meteor2 = new Meteor(2);
    public Meteor meteor3 = new Meteor(4);
    public Meteor meteor4 = new Meteor(6);


    private static GamePanel instance;

    //constructor
    private GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));// window size
        this.setBackground(Color.pink); // background color
        this.setDoubleBuffered(true); // this should improve the game's rendering performance
        this.addKeyListener(keyH); // adding keyboard stuff
        this.setFocusable(true); // the game panel can be "focused" to receive key input
    }

    //game panel instance
    public static GamePanel getInstance() {
        if(instance == null){
            instance = new GamePanel();
        }
        return instance;
    }

    public void startGameThread(){
        gameThread = new Thread(this); // here the Thread is instantiated
        gameThread.start(); // the RUN method will be automatically called
    }

    @Override
    public void run() { // a game loop

        double drawInterval = 1000000000 / FPS; // 1 billion nanoseconds / FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            long currentTime = System.nanoTime();
            if(startTime == 0) {
                startTime = System.nanoTime();
            }

            if(currentTime/1000000 > startTime/1000000 + 200000 && entity.Player.age == 1){
                if(room.room_type != 4 && room.room_type != 8) {
                    entity.Player.age = 2;
                }
            }
            else if(currentTime/1000000 > startTime/1000000 + 100000 && entity.Player.age == 0){
                if(room.room_type != 4 && room.room_type != 8) {
                    entity.Player.age = 1;
                }
            }

            //1.UPDATE - update information (ex. character position)
            update();
            //2.DRAW - draw the screen with the updated information
            repaint(); // calling the paintComponent() method

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; // converting nano -> milliseconds

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update(){
        player.update();
        room.update();

        if(room.room_type == 4) { // football mini-game
            football.update();
        }

        if(room.room_type == 8) { // meteor mini-game
            meteor1.update();
            meteor2.update();
            meteor3.update();
            meteor4.update();
        }

        if(room.bed_pressed){
            player.energy.level = 1;
            room.bed_pressed = false;
        }
        if(room.fridge_pressed){
            player.hunger.level = 1;
            room.fridge_pressed = false;
        }
        if(room.bathtub_pressed){
            player.hygiene.level = 1;
            room.bathtub_pressed = false;
        }
        if(room.room_type == 4 && football.intersects_player){
            player.fun.level = 1;
            football.intersects_player = false;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        room.draw(g2);
        if(room.room_type != 0 && room.room_type != 5 && room.room_type != 6 && room.room_type != 7 && room.room_type != 9) {
            player.draw(g2);
            if(room.room_type == 4){ // football mini-game
                football.draw(g2);
                goal.draw(g2);
                g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
                g2.setColor(Color.white);
                g2.drawString("Score: " + football.score, 1300, 50);
            }
            else if(room.room_type == 8){ // meteor mini-game
                meteor1.draw(g2);
                meteor2.draw(g2);
                meteor3.draw(g2);
                meteor4.draw(g2);
                g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
                g2.setColor(Color.white);
                g2.drawString("Score: " + meteor1.getScore(), 1300, 50);
                g2.drawString("Hits: " + meteor1.getHits(), 1300, 80);
            }
            else{
                player.energy.draw(g2, Color.green, 65, 55);
                player.hunger.draw(g2, Color.yellow, 65, 133);
                player.fun.draw(g2, Color.pink, 280, 55);
                player.hygiene.draw(g2, Color.lightGray, 280, 133);
            }
        }

        g2.dispose();
    }
}
