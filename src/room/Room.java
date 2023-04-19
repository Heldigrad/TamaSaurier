package room;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class Room {

    public int room_type;
    // bedroom = 0; kitchen = 1; bathroom = 2;
    public static final int max_rooms = 2;
    public BufferedImage background;

    KeyHandler keyH;
    public Room(int type, KeyHandler keyH){
        this.room_type = type;
        this.keyH = keyH;
        getRoomImage();
    }

    public void update(){
        if(keyH.prevRoomPressed){
            if(room_type == 0){
                room_type = 2;
            }
            else {
                room_type--;
            }
        }
        else if (keyH.nextRoomPressed){
            if(room_type == 2){
                room_type = 0;
            }
            else {
                room_type++;
            }
        }
        getRoomImage();
    }

    public void getRoomImage(){
        try{
            switch (room_type) {
                case 0:
                    background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("rooms/bedroom_done.png"));
                    break;
                case 1:
                    background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("rooms/kitchen.png"));
                    break;
                case 2:
                    background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("rooms/bathroom_done.png"));
                    break;
                case 3:
                    background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("rooms/menu_done.png"));
                    break;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){

        g2.drawImage(background, 0, 0, 16*3*32, 16*3*16, null);
    }

}
