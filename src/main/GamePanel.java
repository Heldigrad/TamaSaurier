package main;

import entity.*;
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
    public boolean start_game = false;
    public Player player = Player.getInstance();

    //football mini-game
    public Football football = new Football(this);
    public Goal goal = new Goal(this);
    public int high_score_football = 0;

    //meteor mini-game
    public Meteor meteor1 = new Meteor(0);
    public Meteor meteor2 = new Meteor(2);
    public Meteor meteor3 = new Meteor(4);
    public Meteor meteor4 = new Meteor(6);
    public int high_score_meteor = 0;

    // food
    public Food food1 = new Food((int)(Math.random() * (15) + 0));
    public Food food2 = new Food((int)(Math.random() * (15) + 0));
    public Food food3 = new Food((int)(Math.random() * (15) + 0));
    public Food food4 = new Food((int)(Math.random() * (15) + 0));
    public Food food5 = new Food((int)(Math.random() * (15) + 0));
    public Food food6 = new Food((int)(Math.random() * (15) + 0));
    public Food food7 = new Food((int)(Math.random() * (15) + 0));
    public Food food8 = new Food((int)(Math.random() * (15) + 0));
    public Food food9 = new Food((int)(Math.random() * (15) + 0));
    boolean change = false;

    //Card mini-game
    public Card[][] cards = Card.createGame(0);
    public int visibleCount = 0;
    public boolean wait = false;
    public long waitStart = 0;
    public int stage = 1;
    public boolean done = false;
    public boolean started = false;
    public long timer = 0;
    public int high_score_card = 0;

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

        double drawInterval = (double) 1000000000 / FPS; // 1 billion nanoseconds / FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        long foodChange = 0;
        long start_aging = 0;

        long update_db = System.nanoTime();
        while(gameThread != null){
            long currentTime = System.nanoTime();

            if(startTime == 0) {
                startTime = System.nanoTime();
            }

            if(start_game){
                start_aging = System.nanoTime();
            }

            if(start_game && start_aging/1000000 > startTime/1000000 + 200000 && entity.Player.getInstance().age == 1){
                if(room.room_type != 4 && room.room_type != 8) {
                    entity.Player.getInstance().age = 2;
                }
            }
            else if(start_game && start_aging/1000000 > startTime/1000000 + 100000 && entity.Player.getInstance().age == 0){
                if(room.room_type != 4 && room.room_type != 8) {
                    entity.Player.getInstance().age = 1;
                }
            }

            if((currentTime - update_db)/1000000000 >= 10){
                update_db = currentTime;
                player.db.update();
            }

            if(foodChange/1000000 + 10000 < currentTime/1000000){
                foodChange = currentTime;
                change = true;
            }
            else{
                change = false;
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

        if(change){
            food1.update((int)(Math.random() * (15) + 0));
            food2.update((int)(Math.random() * (15) + 0));
            food3.update((int)(Math.random() * (15) + 0));
            food4.update((int)(Math.random() * (15) + 0));
            food5.update((int)(Math.random() * (15) + 0));
            food6.update((int)(Math.random() * (15) + 0));
            food7.update((int)(Math.random() * (15) + 0));
            food8.update((int)(Math.random() * (15) + 0));
            food9.update((int)(Math.random() * (15) + 0));
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
        if(room.room_type != 0 && room.room_type != 5 && room.room_type != 6 && room.room_type != 7 && room.room_type != 9 && room.room_type != 10 && room.room_type != 11 && room.room_type != 12) {
            player.draw(g2);
            if(room.room_type == 4){ // football mini-game
                football.draw(g2);
                goal.draw(g2);
                g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
                g2.setColor(Color.white);
                g2.drawString("Score: " + football.score, 1300, 50);
                if(football.score > high_score_football){
                    high_score_football = football.score;
                }
                g2.drawString("High Score: " + high_score_football, 1300, 80);
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

        else if(room.room_type == 10){
            player.hunger.draw(g2, Color.yellow, 65, 55);
            if(food1.visible){
                food1.draw(g2, 430, 210);
            }
            if(food2.visible){
                food2.draw(g2, 715, 210);
            }
            if(food3.visible){
                food3.draw(g2, 990, 210);
            }
            if(food4.visible){
                food4.draw(g2, 430, 430);
            }
            if(food5.visible){
                food5.draw(g2, 715, 430);
            }
            if(food6.visible){
                food6.draw(g2, 990, 430);
            }
            if(food7.visible){
                food7.draw(g2, 430, 620);
            }
            if(food8.visible){
                food8.draw(g2, 715, 620);
            }
            if(food9.visible){
                food9.draw(g2, 990, 620);
            }
        }

        else if (room.room_type == 11){
            g2.setFont(new Font("Seqoe UI", Font.PLAIN, 32));
            g2.setColor(Color.white);
            g2.drawString("Stage: " + stage, 50, 50);
            g2.drawString("Timer: " + (60 - (System.nanoTime() - timer)/1000000000), 50, 100);

            for(int i = 0; i < cards.length; ++i){
                for(int j = 0; j < cards[0].length; ++j){
                    cards[i][j].draw(g2, cards[i][j].x, cards[i][j].y);
                }
            }
        }

        g2.dispose();
    }
}
