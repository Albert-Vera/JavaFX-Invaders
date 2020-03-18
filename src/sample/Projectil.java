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
    private boolean impacto = true;
    private GraphicsContext gc;
    //private Sprite player = new Sprite(300, 750, 40, 40, "player", Color.BLUE);


    public Projectil (){
        this.posX = 200;
        this.posY = 900;
        this.velX = 20.0f;
        this.velY = 1.5f;
        this.dirX = 1;
        this.dirY = 1;
      //  setImage(image);
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();

    }
    public void render(GraphicsContext gc) {
        gc.drawImage(image, getPosX(), getPosY());
    }

    public void clear(GraphicsContext gc) {
        gc.clearRect(posX,posY, width, height);
    }

    public double move() {
        setPosY(posY -= 20);
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velX) {
        this.velX = velX;
    }
    public void setPosition(double x, double y) {
        this.posX = x ;
        this.posY = y;
    }
    public void setVelocity(double x, double y) {
        this.velX = x;
        this.velY = y;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public boolean getImpacto() {
        return impacto;
    }

    public Image getImage() {
        return image;
    }

    public void setImpacto(boolean impacto) {
        this.impacto = impacto;
    }
}
