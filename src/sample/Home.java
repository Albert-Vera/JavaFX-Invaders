package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;



import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private int APP_HEIGHT = 1800;
    private int APP_WIDTH = 2100;
    private int SPACE = 40;
    private int cantidadNaves = 10;
    ArrayList<Sprite> marcianoNaves;
    ArrayList<Sprite> misil = new ArrayList<>();
    private Scene scene;
    private GraphicsContext gc;
    Player player;


    @FXML
    ImageView fondoPantalla;
    @FXML
    AnchorPane anchorPaneFondo;
    @FXML Canvas canvas;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.117), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            player.clear(gc);
            //player.move(scene);
            player.render(gc);
            detectarTeclado();
            renderitzarMarciano();
            renderitzarMisil();
            colisiones();
        }
    })
    );

    private void colisiones() {
        Iterator<Sprite> impactesIter = marcianoNaves.iterator();
        while (impactesIter.hasNext()){
            Sprite marciano = impactesIter.next();
            Iterator<Sprite> misilLanzado = misil.iterator();
            while (misilLanzado.hasNext()){
                Sprite proyectil = misilLanzado.next();
                if(proyectil.intersects(marciano)){
                    System.out.println("posicon x: " + marciano.getPosX()+ "  posicon y: " + marciano.getPosY());
                    impactesIter.remove();
                    misilLanzado.remove();
                    marciano.clear(gc, marciano.getPosX(), marciano.getPosY());
                    proyectil.clear(gc, proyectil.getPosX(), proyectil.getPosY());
                   explosion(marciano.getPosX(), marciano.getPosY());
                  // subir score
                }
            }
        }
    }
    private void explosion(double x, double y){
        Sprite sprite = new Sprite();
        sprite.clear(gc, x-30,y-10);
       //sprite.setImage("images/exlo3.png");
        sprite.setPosition(x-30,y-10);
        marcianoNaves.add(sprite);


    }

    public void renderitzarMarciano() {
        for ( Sprite nau: marcianoNaves){
            nau.clear(gc, nau.getPosX(), nau.getPosY());
            nau.setPosition(nau.moveX(scene), nau.moveY(scene));
            nau.render(gc);
        }
    }
    private void renderitzarMisil() {
        for (int i = 0; i < misil.size() - 1; i++) {
            misil.get(i).clear(gc, misil.get(i).getPosX(), misil.get(i).getPosY());
            misil.get(i).setPosition( misil.get(i).getPosX(), misil.get(i).move());
            misil.get(i).render(gc);
            //  misil.get(i).
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imageFondo = new Image("images/fondo.jpg", 4000, 2700,false, false);
        fondoPantalla.setImage(imageFondo);
        player = new Player(new Image("images/player.png"));
        crearMarciano();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("OCR A Std", 30));
        gc.fillText("SCORE<1>\t\t\t\t\t\t    HI-SCORE\t\t\t\t\t\t\t\t  SCORE<2>", 30, 30);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    void detectarTeclado(){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.RIGHT){
                   player.moveRight();
                }
                if (event.getCode() == KeyCode.LEFT){
                  player.moveLeft();
                }
                if (event.getCode() == KeyCode.SPACE){
                    disparar();
                }
            }
        });
    }
    public void setScene(Scene sc) {
        scene = sc;
    }

    public void crearMarciano(){
       marcianoNaves = new ArrayList<>();
        for (int y = 165, i= 0 ; y < 450 && i < 3 ; y += 120, i++) {
            for (int x = 180, e = 0; x < 1700 && e < cantidadNaves; x += 140, e++) {

                Sprite marciano = new Sprite();
                marciano.setImage(new Image("images/ufo.png",75,35,false,false));
                marciano.setPosition(x,y);
                marcianoNaves.add(marciano);
                // totalEnemies++;
            }
        }
    }
    public void disparar() {
        Sprite disparo = new Sprite();
        disparo.setImage(new Image("images/projectil.png", 15,15,false, false));
        disparo.setPosition(player.getPosX() + 40, player.getPosY() - 20);
        misil.add(disparo);
        System.out.println("playe x: " + player.getPosX());

    }



}
