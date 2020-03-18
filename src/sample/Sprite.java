package sample;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite
{
    private Image image;
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private int dirX, dirY;
    private double width;
    private double height;

    public Sprite()
    {
        posX = 0;
        posY = 0;
        this.velX = 20.0f;
        this.velY = 1.5f;
        this.dirX = 1;
        this.dirY = 1;
    }

    public void setImage(Image i)
    {
        image = i;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        posX = x;
        posY = y;
    }

    public void setVelocity(double x, double y)
    {
        velX = x;
        velY = y;
    }

    public void addVelocity(double x, double y)
    {
        velX += x;
        velY += y;
    }

    public void update(double time)
    {
        posX += velX * time;
        posY += velY * time;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage( image, posX, posY);
    }
    public void clear(GraphicsContext gc, double x, double y) {
        gc.clearRect(x ,y, width, height);
    }
    public double move() {
        posY -= 20;
        return posY;
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

    public double getPosY() {
        return posY;
    }

    public String toString()
    {
        return " Position: [" + posX + "," + posY + "]"
                + " Velocity: [" + velX + "," + velY + "]";
    }
}