package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Card extends Entity{

    public boolean visible;
    public boolean permanently_visible;
    public int type;

    public Card(int type, int x, int y){
        setImage(type);
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = 24;
        this.height = 32;
        this.speed = 0;
        visible = false;
        permanently_visible = false;
    }

    public void setImage(int type){
        try {
            if(visible || permanently_visible) {
                switch (type) {
                    case 0: // card_bee
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_bee.png")));
                        break;
                    case 1: // card_chicken
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_chicken.png")));
                        break;
                    case 2: // card_computer
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_computer.png")));
                        break;
                    case 3: // card_flower
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_flower.png")));
                        break;
                    case 4:
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_turtle.png")));
                        break;
                    case 5:
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_frog.png")));
                        break;
                    default:
                        image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card.png")));
                        break;
                }
            }
            else {
                image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card.png")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shuffle_matrix(Card[][] cards){
        Random rand = new Random();

        for (int i = 0; i < cards.length; ++i) {
            for(int j = 0; j < cards[0].length; ++j){
                int randomIToSwap = rand.nextInt(cards.length);
                int randomJtoSwap = rand.nextInt(cards[0].length);
                int temp = cards[randomIToSwap][randomJtoSwap].type;
                cards[randomIToSwap][randomJtoSwap].type = cards[i][j].type;
                cards[i][j].type = temp;
                cards[i][j].visible = false;
                cards[i][j].permanently_visible = false;
                cards[i][j].setImage(cards[i][j].type);
            }
        }
    }

    public static void printMatrix(Card[][] cards){
        for (int i = 0; i < cards.length; ++i) {
            for(int j = 0; j < cards[0].length; ++j) {
                System.out.println(cards[i][j].type + " ");
            }
        }
    }

    public static void card_click(Card[][] cards, int x, int y) {
        Card aux;

        if(GamePanel.getInstance().visibleCount == 0 || GamePanel.getInstance().visibleCount == 1) {
            for (int i = 0; i < cards.length; ++i) {
                for (int j = 0; j < cards[0].length; ++j) {
                    aux = cards[i][j];
                    if (x >= aux.x && x <= aux.x + aux.width * 6 && y >= aux.y && y <= aux.y + aux.height * 6) { // checking if the card was clicked
                        cards[i][j].visible = true;
                        cards[i][j].setImage(cards[i][j].type);
                        GamePanel.getInstance().visibleCount++;
                    }
                }
            }
        }
        if(GamePanel.getInstance().visibleCount == 2){
            int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
            int ok = 0;
            for (int i = 0; i < cards.length; ++i) {
                for(int j = 0; j < cards[0].length; ++j) {
                    if(cards[i][j].visible){
                        if(ok == 0) {
                            i1 = i;
                            j1 = j;
                            ok++;
                        }
                        else{
                            i2 = i;
                            j2 = j;
                            break;
                        }
                    }
                }
            }
            ok = 0;
            if(cards[i1][j1].type == cards[i2][j2].type){
                cards[i1][j1].permanently_visible = true;
                cards[i2][j2].permanently_visible = true;
                cards[i1][j1].visible = false;
                cards[i2][j2].visible = false;
                cards[i1][j1].setImage(cards[i1][j1].type);
                cards[i2][j2].setImage(cards[i2][j2].type);
                int okk = 1;
                for (int i = 0; i < cards.length; ++i) {
                    for(int j = 0; j < cards[0].length; ++j) {
                        if(!cards[i][j].permanently_visible){
                            okk = 0;
                        }
                    }
                }
                GamePanel.getInstance().done = okk == 1;
                if(GamePanel.getInstance().done){
                    GamePanel.getInstance().stage++;
                    GamePanel.getInstance().wait = true;
                    GamePanel.getInstance().waitStart = System.nanoTime();

                }

            } else {
                GamePanel.getInstance().wait = true;
                GamePanel.getInstance().waitStart = System.nanoTime();
            }

            GamePanel.getInstance().visibleCount = 0;
        }
    }

    public static Card[][] createGame(int level){
        int n = 0, m = 0;
        int[] layouti = new int[0];
        int[] layoutj = new int[0];
        if(level == 0) {
            n = 3; m = 2;
            layouti = new int[]{350, 665, 1000};
            layoutj = new int[]{150, 390};
        }
        if(level == 1){
            n = 4; m = 2;
            layouti = new int[]{140, 460, 850, 1130};
            layoutj = new int[]{150, 390};
        }
        if(level > 1){
            n = 4; m = 3;
            layouti = new int[]{215, 500, 800, 1048};
            layoutj = new int[]{60, 286, 490};
        }

        Card[][] game = new Card[n][m];
        int x = 0;
        int k = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                game[i][j] = new Card(x, layouti[i], layoutj[j]);
                k++;
                if(k == 2){
                    x++;
                    k = 0;
                }
            }
        }
        printMatrix(game);
        System.out.println("da");
        shuffle_matrix(game);
        System.out.println("da");
        printMatrix(game);

        return game;
    }

    public static void update(Card[][] cards){
        shuffle_matrix(cards);
        for (int i = 0; i < cards.length; ++i) {
            for (int j = 0; j < cards[0].length; ++j) {
                cards[i][j].setImage(cards[i][j].type);
            }
        }
    }

    public static void resize(int level){
        GamePanel.getInstance().cards = createGame(level);
        shuffle_matrix(GamePanel.getInstance().cards);
    }

    public void draw(Graphics2D g2, int cx, int cy){
        g2.drawImage(image, cx,cy, width*6, height*6, null);
    }
}
