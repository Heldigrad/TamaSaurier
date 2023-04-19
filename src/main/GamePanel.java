package main;

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

    KeyHandler keyH = new KeyHandler();
    Thread gameThread; // once a thread is started, it keeps the program running until you stop it
    //when it's started, the RUN method found below is automatically called

    Player player = new Player(this);
    Room room = new Room(0, keyH);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));// window size
        this.setBackground(Color.pink); // background color
        this.setDoubleBuffered(true); // this should improve the game's rendering performance
        this.addKeyListener(keyH); // adding keyboard stuff
        this.setFocusable(true); // the game panel can be "focused" to receive key input
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

            if(currentTime/1000000 > startTime/1000000 + 20000){
                entity.Player.age = 2;
            }
            else if(currentTime/1000000 > startTime/1000000 + 10000){
                entity.Player.age = 1;
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
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        room.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
