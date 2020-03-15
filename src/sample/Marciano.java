package sample;


import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Marciano {

    Home home = new Home();
    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

//    public Marciano() {
//    }

    public Marciano(Image image, int x , int y){
        this.posX = x;
        this.posY = y;
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
    public void setPosition(double x, double y) {
        this.posX = x;
        this.posY = y;
    }
    public double getPositionX() {
        return posX;
    }

    public double getPositionY() {
        return posY;
    }
    public Image getImage() {
        return image;
    }

    public void render(GraphicsContext gc, Image image, double x, double y) {

        gc.drawImage(image, x, y);
    }
    public void clear(GraphicsContext gc, double x, double y) {
        gc.clearRect(x ,y, width, height);


    }
    public double moveX(Scene scene){
        if(dirX == 1) {
            posX += velX;
        }else {
            posX -= velX;
        }
        return posX;
    }
    public double moveY(Scene scene){
        if(dirX == 1) {
            posX += velX;

            if(posX>=1650-width) {
                dirX = (-1) * dirX;
                posY += 60;
            }
        }else {
            posX -= velX;
            if(posX <= 10) {
                dirX = (-1)*dirX;
                posY += 60;
            }
        }
        return posY;
    }
}
