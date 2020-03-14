package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Projectil  {
    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;
    private GraphicsContext gc;
    //private Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE);


    public Projectil (Image image){
        this.posX = 200;
        this.posY = 900;
        this.velX = 20.0f;
        this.velY = 1.5f;
        this.dirX = 1;
        this.dirY = 1;
        setImage(image);
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();

    }
    public void render(GraphicsContext gc) {

        gc.drawImage(image, posX, posY);
    }

    public void clear(GraphicsContext gc) {
        gc.clearRect(posX-150,posY, width+300, height+20);
    }

    public void move(Scene scene) {

    }
}
