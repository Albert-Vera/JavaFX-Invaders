package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Player {

    private Image image;
    Home home = new Home();
    Projectil projectil ;

    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Player (Image image){
        this.posX = 200;
        this.posY = 1065;
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

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {


                if (event.getCode() == KeyCode.RIGHT){
                    posX += velX;
                }
                if (event.getCode() == KeyCode.LEFT){
                    posX -= velX;
                }
                if (event.getCode() == KeyCode.SPACE){

                    disparar();
                    //new Projectil().disparar();

                }

            }
        });
    }
public void disparar() {

   // projectil.setImage(new Image("images/projectil.png", 15, 15, false, false));
    projectil.setPosition(getPosX() + 40, getPosY() - 20);
    // disparo.setVelocity(0, -350);
    // disparo.render(gc);
    //misil.add(disparo);
    // System.out.println(getPosX());
}
//    for ( Projectil a: misil)
//
//        System.out.println("Pos x: " + a.getPosX() + "  pos y:_ "+ a.getPosY() + " -... velocit y.. "+ a.getVelY());
//}
    public void moveRight(){
        setPosX(posX += velX);
    }
    public void moveLeft(){
        setPosX(posX -= velX);
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
