package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Food extends Entity{
    public int saturation;
    public int type;
    public boolean visible = true;

    public boolean meat = false;
    public boolean veggie = false;
    public boolean milk = false;
    public boolean sweet = false;

    public static boolean food_timeout = false;
    public static long food_timeout_start = 0;

    public Food(int type){
        setCharacteristics(type);
    }

    public void update(int type){
        setCharacteristics(type);
        setVisiblity();
    }

    public void setCharacteristics(int type){
        x = 0; y = 0;
        speed = 0;
        try {
            switch (type) {
                case 0 -> { // beans_soup
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/beans_soup.png")));
                    saturation = 75;
                    this.type = type;
                    width = 13;
                    height = 11;
                    veggie = true;
                    meat = false;
                    milk = false;
                    sweet = false;
                }
                case 1 -> { // chicken_soup
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/chicken_soup.png")));
                    saturation = 80;
                    this.type = type;
                    width = 13;
                    height = 11;
                    veggie = false;
                    meat = true;
                    milk = false;
                    sweet = false;
                }
                case 2 -> { // chips
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/chips.png")));
                    saturation = 25;
                    this.type = type;
                    width = 17;
                    height = 19;
                    veggie = true;
                    meat = false;
                    milk = false;
                    sweet = false;
                }
                case 3 -> { // fries
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/fries.png")));
                    saturation = 45;
                    this.type = type;
                    width = 14;
                    height = 16;
                    veggie = true;
                    meat = false;
                    milk = false;
                    sweet = false;
                }
                case 4 -> { // lasagna
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/lasagna.png")));
                    saturation = 100;
                    this.type = type;
                    width = 17;
                    height = 15;
                    veggie = false;
                    meat = true;
                    milk = false;
                    sweet = false;
                }
                case 5 -> { // milk and pasta = lapte cu fidea
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/milk_and_pasta.png")));
                    saturation = 35;
                    this.type = type;
                    width = 14;
                    height = 12;
                    veggie = false;
                    meat = false;
                    milk = true;
                    sweet = true;
                }
                case 6 -> { // milk and semolina = lapte cu gris
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/milk_and_semolina.png")));
                    saturation = 15;
                    this.type = type;
                    width = 14;
                    height = 12;
                    veggie = false;
                    meat = false;
                    milk = true;
                    sweet = true;
                }
                case 7 -> { // pancake = clatita
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/pancake.png")));
                    saturation = 30;
                    this.type = type;
                    width = 14;
                    height = 12;
                    veggie = false;
                    meat = false;
                    milk = true;
                    sweet = true;
                }
                case 8 -> { // pizza
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/pizza.png")));
                    saturation = 70;
                    this.type = type;
                    width = 14;
                    height = 11;
                    veggie = false;
                    meat = true;
                    milk = true;
                    sweet = false;
                }
                case 9 -> { // polenta = mamaliga
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/polenta.png")));
                    saturation = 30;
                    this.type = type;
                    width = 14;
                    height = 12;
                    veggie = true;
                    meat = false;
                    milk = false;
                    sweet = false;
                }
                case 10 -> { // salad
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/salad.png")));
                    saturation = 40;
                    this.type = type;
                    width = 14;
                    height = 13;
                    veggie = true;
                    meat = false;
                    milk = false;
                    sweet = false;
                }
                case 11 -> { // sandwich
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/sandwich.png")));
                    saturation = 30;
                    this.type = type;
                    width = 13;
                    height = 13;
                    veggie = false;
                    meat = true;
                    milk = false;
                    sweet = false;
                }
                case 12 -> { // sarma
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/sarma.png")));
                    saturation = 80;
                    this.type = type;
                    width = 13;
                    height = 11;
                    veggie = false;
                    meat = true;
                    milk = true;
                    sweet = false;
                }
                case 13 -> { // spinach
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/spinach.png")));
                    saturation = 40;
                    this.type = type;
                    width = 14;
                    height = 12;
                    veggie = true;
                    meat = false;
                    milk = false;
                    sweet = false;
                }
                case 14 -> { // steak
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/steak.png")));
                    saturation = 55;
                    this.type = type;
                    width = 19;
                    height = 16;
                    veggie = false;
                    meat = true;
                    milk = false;
                    sweet = false;
                }
                case 15 -> { // yoghurt
                    image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("stuff/yoghurt.png")));
                    saturation = 40;
                    this.type = type;
                    width = 10;
                    height = 12;
                    veggie = false;
                    meat = false;
                    milk = true;
                    sweet = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eat(){
        if(food_timeout){
            if((System.nanoTime() - food_timeout_start)/1000000000 >= 1){
                food_timeout = false;
            }
            else{
                System.out.println("timeout");
                return;
            }
        }


        if (visible) {
            if(Player.getInstance().check_love(this)) {
                Player.getInstance().hunger.level += (float) 1 / 100 * saturation;
                Player.getInstance().setAffinities(this);
                visible = false;
            }
            else{
                food_timeout = true;
                food_timeout_start = System.nanoTime();
            }
        }
    }

    public void setVisiblity(){
        int probability = (int)(Math.random() * (3) + 0);
        visible = probability == 2;
    }

    public void draw(Graphics2D g2, int cx, int cy){
        g2.drawImage(image, cx,cy, width*7, height*7, null);
    }
}
