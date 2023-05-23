package main;
import entity.Food;

public class FoodFactory {
    public static Food makeFood() {
        int x = (int)(Math.random() * (15) + 0);
        return new Food(x);
    }
}
