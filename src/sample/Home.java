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


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Home implements Initializable {
    private int SPACE = 40;
    private int cantidadNaves = 10;
    ArrayList<Sprite> marcianoNaves;
    ArrayList<Sprite> misil = new ArrayList<>();
    private Scene scene;
    private GraphicsContext gc;
    private Player player;
    private SoundEffect  explosionEffect;


    String s = getClass().getClassLoader().getResource("sound/soexplosio.wav").toExternalForm();
    Media sound = new Media(s);
    MediaPlayer audioClip = new MediaPlayer(sound);
    Image imageFondo;


    @FXML
    ImageView fondoPantalla;
    @FXML
    AnchorPane anchorPaneFondo;
    @FXML Canvas canvas;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.217), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            //pintar la imatge neteja la resta d'spprites i no cal fer el clear
            gc.drawImage(imageFondo,0,0);
            //player.clear(gc);
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
                    impactesIter.remove();
                    misilLanzado.remove();
//                    marciano.clear(gc, marciano.getPosX(), marciano.getPosY());
//                    proyectil.clear(gc, proyectil.getPosX(), proyectil.getPosY());
                    explosion(marciano.getPosX(), marciano.getPosY());
                    // subir score
                }
            }
        }
    }


    private void explosion(double x, double y){
        Sprite sprite = new Sprite();
        //sprite.clear(gc, x-30,y-10);
        sprite.setImage(new Image("images/explosionn.png",105,105,false,false));
        sprite.setPosition(x-20,y-30);
            sprite.render(gc);
//        File filestring = new File("sound/soexplosio.wav");
//        Media file = new Media(filestring.toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(file);
//        mediaPlayer.autoPlayProperty();
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.play();

        audioClip.play();



    }

    public void renderitzarMarciano() {
        for ( Sprite nau: marcianoNaves){
          //  nau.clear(gc, nau.getPosX(), nau.getPosY());
            nau.setPosition(nau.moveX(), nau.moveY());
            nau.render(gc);

        }
    //    for ( Sprite nau: marcianoNaves){
            // nau.clear(gc, nau.getPosX(), nau.getPosY());
//            if (nau.getPosX() >= 1500){
//                int as = 0;
//                boolean ds = false;
//                for (Sprite a: marcianoNaves) {
////                    if (a.getId_nave() == 0) {
////                        System.out.println("get-90  : " + (a.getPosX()-90)+ "  " + a.getPosX()+ "  y: "+a.getPosY());
////                        a.setPosition(a.getPosX(, a.getPosY()+60);
////                        a.setDirX((-1) * a.getDirX());
////                    }else
//                    a.setDirX((-1) * a.getDirX());
//                    a.setPosY( a.getPosY()+60);
//
//                }
//                break;
//            }
//            nau.setPosX(nau.moveX(marcianoNaves, nau.getId_nave()));
//            nau.render(gc);
//        }
    }
    private void renderitzarMisil() {
        for (int i = 0; i < misil.size() - 1; i++) {
            //misil.get(i).clear(gc, misil.get(i).getPosX(), misil.get(i).getPosY());
            misil.get(i).setPosition( misil.get(i).getPosX(), misil.get(i).move());
            misil.get(i).render(gc);
            //  misil.get(i).
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPaneFondo.setPrefHeight(Mides.APP_HEIGHT);;
        anchorPaneFondo.setPrefWidth(Mides.APP_WIDTH);
        canvas.setWidth(Mides.APP_WIDTH);
        canvas.setHeight(Mides.APP_HEIGHT);
       // System.out.println("duració:" + sound.getDuration().toString() + " loc:" + sound.getSource());
       // setSoundEffects();
        audioClip.setCycleCount(MediaPlayer.INDEFINITE);

        //audioClip.setCycleCount(8);
        imageFondo = new Image("images/fondo.jpg", Mides.APP_WIDTH, Mides.APP_HEIGHT,false, false);
        fondoPantalla = new ImageView(imageFondo);
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
                marciano.setId_nave(e);
//                if (marciano.getId_nave()==0)  marciano.setImage(new Image("images/1.png",75,35,false,false));
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
    }
//    private void setSoundEffects() {
//
//        explosionEffect = new SoundEffect("/home/albert/IdeaProjects/Invaders/src/sound/soexplosio.wav");
//
//    }


}