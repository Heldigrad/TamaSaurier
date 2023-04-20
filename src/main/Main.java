package main;

import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame(); // created a new window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //the window can be closed
        window.setResizable(false); // the window can't be resized
        window.setTitle("TamaSaurier"); // the window's name

        MouseHandler mouse = new MouseHandler();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.addMouseListener(mouse);

        window.pack();

        window.setLocationRelativeTo(null); // the window's location is the center of the screen
        window.setVisible(true); // the window is visible

        gamePanel.startGameThread();
    }

}
