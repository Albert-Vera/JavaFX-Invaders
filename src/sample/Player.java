package sample;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Player {

    private Image image;

    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Player (Image image){
        this.posX = 200;
        this.posY = 1245;
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
    public void moveRight(){
        setPosX(posX += velX);
    }
    public void moveLeft(){
        setPosX(posX -= velX);
    }
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(posX, posY,width,height);
    }
    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
