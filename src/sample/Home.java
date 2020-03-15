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
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private int APP_HEIGHT = 1800;
    private int APP_WIDTH = 2100;
    private int SPACE = 40;
    private int cantidadNaves = 10;
    List<Projectil> misil = new ArrayList<>();
    private Scene scene;
    private GraphicsContext gc;
    Player player;
    Marciano[][] marcianoArray = new Marciano[3][10];
    Projectil projectil;

    FondoPantalla fondo;

    @FXML
    ImageView fondoPantalla;
    @FXML
    AnchorPane anchorPaneFondo;
    @FXML Canvas canvas;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.117), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            player.clear(gc);
            player.move(scene);
            player.render(gc);
           // detectarTeclado();

            for (int i = 0; i < 3; i++) {  //  Esborrar / Moure / Printar naus
                for (int j = 0; j < cantidadNaves; j++) {
                    marcianoArray[i][j].clear(gc, marcianoArray[i][j].getPositionX(), marcianoArray[i][j].getPositionY());
                    marcianoArray[i][j].setPosition(marcianoArray[i][j].moveX(scene), marcianoArray[i][j].moveY(scene));
                    marcianoArray[i][j].render(gc, marcianoArray[i][j].getImage(), marcianoArray[i][j].getPositionX(), marcianoArray[i][j].getPositionY());
                }
            }
            projectil.clear(gc);
            projectil.move();
            projectil.render(gc);

        }
    })
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imageFondo = new Image("images/fondo.jpg", 4000, 2700,false, false);

        fondoPantalla.setImage(imageFondo);
        player = new Player(new Image("images/player.png"));
        projectil = new Projectil(new Image("images/projectil.png", 15,15,false, false));
        crearMarciano();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("OCR A Std", 30));
        gc.fillText("SCORE<1>\t\t\t\t\t\t    HI-SCORE\t\t\t\t\t\t\t\t  SCORE<2>", 30, 30);


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
//    void detectarTeclado(){
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.RIGHT){
//                   player.moveRight();
//                }
//                if (event.getCode() == KeyCode.LEFT){
//                  player.moveLeft();
//                }
//                if (event.getCode() == KeyCode.SPACE){
//                    if (misil.get(0).getImpacto())
//                    disparar();
//                    //new Projectil().disparar();
//
//                }
//            }
//        });
//    }
    public void setScene(Scene sc) {
        scene = sc;
    }

    public void crearMarciano(){
        for (int y = 165, i= 0 ; y < 450 && i < 3 ; y += 120, i++) {
            for (int x = 180, e = 0; x < 1700 && e < cantidadNaves; x += 140, e++) {
                // if (y < 90) {
                marcianoArray[i][e] = insertarMarciano(x,y, "images/ufo.png");
                // totalEnemies++;
            }
        }
    }
    private Marciano insertarMarciano(int x, int y, String imagePath) {
        Marciano nave = new Marciano(new Image(imagePath), x,y);
        nave.setImage(new Image(imagePath));
        nave.setPosition(x, y);
        return nave;
    }
//    public void disparar() {
//
//        disparo.setImage(new Image("images/projectil.png", 15,15,false, false));
//        disparo.setPosition(player.getPosX() + 40, player.getPosY() - 20);
//       // disparo.setVelocity(0, -350);
//       // disparo.render(gc);
//        misil.add(disparo);
//        System.out.println(player.getPosX());
//        misil.get(0).setImpacto(false);
//        for ( Projectil a: misil)
//
//        System.out.println("Pos x: " + a.getPosX() + "  pos y:_ "+ a.getPosY() + " -... velocit y.. "+ a.getVelY());
//    }



}

//    Sprite player = new Sprite(canvas, playerAnimation, 'Player', 350, 620, 40, 30, Lookup.EMPTY);
//            player.setAnimation(playerAnimation);
//                    player.addAction(KeyCode.LEFT, ActionFactory.createMoveAction(playerAnimation, 'left', -4, 0, 0, 0));
//                    player.addAction(KeyCode.RIGHT, ActionFactory.createMoveAction(playerAnimation, 'right', 4, 0, 0, 0));
//                    player.addAction(KeyCode.UP, new ShootAction(playerAnimation, 'fire', new BulletProvider(), new HitHandler(), shootSound));