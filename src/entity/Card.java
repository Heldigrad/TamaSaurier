package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

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
                    case 0 -> // card_bee
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_bee.png")));
                    case 1 -> // card_chicken
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_chicken.png")));
                    case 2 -> // card_computer
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_computer.png")));
                    case 3 -> // card_flower
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_flower.png")));
                    case 4 ->
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_turtle.png")));
                    case 5 ->
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card_frog.png")));
                    default ->
                            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/card.png")));
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

        for (Card[] card : cards) {
            for (int j = 0; j < cards[0].length; ++j) {
                int randomIToSwap = rand.nextInt(cards.length);
                int randomJtoSwap = rand.nextInt(cards[0].length);
                int temp = cards[randomIToSwap][randomJtoSwap].type;
                cards[randomIToSwap][randomJtoSwap].type = card[j].type;
                card[j].type = temp;
                card[j].visible = false;
                card[j].permanently_visible = false;
                card[j].setImage(card[j].type);
            }
        }
    }

    public static void card_click(Card[][] cards, int x, int y) {
        Card aux;

        if(GamePanel.getInstance().visibleCount == 0 || GamePanel.getInstance().visibleCount == 1) {
            for (Card[] card : cards) {
                for (int j = 0; j < cards[0].length; ++j) {
                    aux = card[j];
                    if (x >= aux.x && x <= aux.x + aux.width * 6 && y >= aux.y && y <= aux.y + aux.height * 6) { // checking if the card was clicked
                        card[j].visible = true;
                        card[j].setImage(card[j].type);
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
            if(cards[i1][j1].type == cards[i2][j2].type){
                cards[i1][j1].permanently_visible = true;
                cards[i2][j2].permanently_visible = true;
                cards[i1][j1].visible = false;
                cards[i2][j2].visible = false;
                cards[i1][j1].setImage(cards[i1][j1].type);
                cards[i2][j2].setImage(cards[i2][j2].type);
                int okk = 1;
                for (Card[] card : cards) {
                    for (int j = 0; j < cards[0].length; ++j) {
                        if (!card[j].permanently_visible) {
                            okk = 0;
                            break;
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
            layouti = new int[]{390, 575, 775, 970};
            layoutj = new int[]{150, 390};
        }
        if(level > 1){
            n = 4; m = 3;
            layouti = new int[]{390, 575, 775, 970};
            layoutj = new int[]{60, 276, 490};
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
        shuffle_matrix(game);
        return game;
    }

    public static void resize(int level){
        GamePanel.getInstance().cards = createGame(level);
        shuffle_matrix(GamePanel.getInstance().cards);
    }

    public void draw(Graphics2D g2, int cx, int cy){
        g2.drawImage(image, cx,cy, width*6, height*6, null);
    }
}
