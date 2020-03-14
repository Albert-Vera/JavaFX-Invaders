package sample;


import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Marciano {

    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Marciano(Image image){
        this.posX = 400;
        this.posY = 165;
        this.velX = 2.0f;
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
        for (int i = 0; i < 1200; i+= 150) {
            //posX = posX+ i;
            gc.drawImage(image, posX+i, posY);
            System.out.println("position: "+ (posX+i));
        }
        for (int i = 0; i < 1200; i+= 150) {
            //posX = posX+ i;
            gc.drawImage(image, posX+i, posY+120);
            System.out.println("position: "+ (posX+i));
        }
        for (int i = 0; i < 1200; i+= 150) {
            //posX = posX+ i;
            gc.drawImage(image, posX+i, posY+240);
            System.out.println("position: "+ (posX+i));
        }
;
    }
    public void clear(GraphicsContext gc) {
        for (int i = 0; i < 1200; i+= 150) {
            gc.clearRect(posX + i,posY, width, height);

        }
        for (int i = 0; i < 1200; i+= 150) {
            gc.clearRect(posX + i,posY + 120, width, height);

        }
        for (int i = 0; i < 1200; i+= 150) {
            gc.clearRect(posX + i,posY + 240, width, height);

        }
    }
    public void move(Scene scene){
        if(dirX == 1) {
            posX += velX*2;
            if(posX>=640-width) {
                dirX = (-1)*dirX;
                posY += 30;
            }
        }else {
            posX -= velX*2;
            if(posX<=0) {
                dirX = (-1)*dirX;
                posY += 30;
            }
        }
//        if(dirY == 1){
//            posY += velY;
//            if(posY>=400-height) dirY = (-1)*dirY;
//        }
//        else {
//            posY -= velY;
//            if(posY<=0) dirY = (-1)*dirY;
//        }
    }
}
