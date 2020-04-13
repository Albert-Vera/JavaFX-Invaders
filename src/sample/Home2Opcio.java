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

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Home2Opcio implements Initializable {
    //private Sprite[][] marcianoNaves = new Sprite[3][10];
    //private Sprite[][] enemiesMoved = new Sprite[3][10];
    //private Sprite[][] currentEnemies;
    private int SPACE = 40;
    private int score = 0 ;
    private int cantidadNaves = 10;
    private int limiteDiparos = 14;
    private int totalEnemics = 30;
    private int posLimitDisparo = 230;
    private int posicionUltimoDisparo = 1150;
    private int posXprimeraNau = 180;
    private int posYprimeraNau = 165;
    private int incrementPosNauX = 140;
    private int incrementPosNauY = 140;
    private int moviment = 15;
    private int limitDretScreen = 320;
    private boolean misilRetarder = true;
    private ArrayList<Sprite> marcianoNaves;
    ArrayList<Sprite> misil = new ArrayList<>();
    private Scene scene;
    private GraphicsContext gc;
    private Player player;
    private SoundEffect  explosionEffect;


    private String s = getClass().getClassLoader().getResource("sound/soexplosio.wav").toExternalForm();
    private String disparo = getClass().getClassLoader().getResource("sound/disparo2.mp3").toExternalForm();

    Media sound = new Media(s);
    Media laser = new Media(disparo);
    Image imageFondo;
    @FXML
    ImageView fondoPantalla;
    @FXML
    AnchorPane anchorPaneFondo;
    @FXML Canvas canvas;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.217), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            gc.drawImage(imageFondo,0,0);
            gc.fillText("SCORE<1> "+ score +"\t\t\t\t\t\t    HI-SCORE\t\t\t\t\t\t\t\t  SCORE<2>", 30, 30);
            player.render(gc);
            detectarTeclado();
            //crearMarciano();
            renderitzarMarciano();
            posXprimeraNau += moviment;

            if (posXprimeraNau >= limitDretScreen) {
                posYprimeraNau += 50;
                moviment = (-15);
            }
            if (posXprimeraNau <= 50) {
                posYprimeraNau += 50;
                moviment = (+15);
            }
            renderitzarMisil();
            colisiones();

            if (totalEnemics == 0) finalPantalla();
        }
    })
    );
    void finalPantalla(){
        System.out.println("terminado" + totalEnemics);
    }

    private void colisiones() {

//        for (int i = 0; i < marcianoNaves.length; i++) {
//            for (int j = 0; j < marcianoNaves[i].length; j++) {
//
//                Iterator<Sprite> misilLanzado = misil.iterator();
//                while (misilLanzado.hasNext()) {
//                    Sprite proyectil = misilLanzado.next();
//                    if (marcianoNaves[i][j] != null) {
//                        if (proyectil.intersects(marcianoNaves[i][j])) {
//                            System.out.println("impacto.en x .." + i +"  Y: " + j);
//                            explosion(i, j);
//                            totalEnemics--;
//                            misilLanzado.remove();
//                            marcianoNaves[i][j] = null;
//                        }
//                    }
//                }
//            }
//        }

        int a = 0;
        Iterator<Sprite> impactesIter = marcianoNaves.iterator();
        while (impactesIter.hasNext()) {
            a++;

            Sprite marciano = impactesIter.next();
            Iterator<Sprite> misilLanzado = misil.iterator();
            while (misilLanzado.hasNext()) {
                Sprite proyectil = misilLanzado.next();
                if (proyectil.intersects(marciano)) {
                    try {
                        // marcianoNaves.get(a).setId_nave(100);
                        impactesIter.remove();
                        misilLanzado.remove();
                        explosion(marciano.getPosX(), marciano.getPosY());
                        totalEnemics--;
                        score += 10;
                    } catch (Exception e) {
                        System.out.println("errorrrr  en colisiones");
                    }
                    // subir score
                } else {
                    if (proyectil.getPosY() < posLimitDisparo) misilLanzado.remove();
                }
            }
        }
    }


    private void explosion(double x, double y){
        Sprite sprite = new Sprite();
        sprite.setImage(new Image("images/explosionn.png",105,105,false,false));
        sprite.setPosition(x-20,y-30);
        sprite.render(gc);
        MediaPlayer audioClip = new MediaPlayer(sound);
        audioClip.volumeProperty();
        audioClip.play();
    }

    public void renderitzarMarciano() {


//        Sprite sprite = new Sprite();
//        for ( Sprite nau: marcianoNaves) {
//
//            //System.out.println("cosas................" + posXprimeraNau + "  " + posYprimeraNau);
//            nau.setPosition(posXprimeraNau, posYprimeraNau);
//            nau.render(gc);
//            posXprimeraNau += incrementPosNauX;
//            //System.out.println("id.........id...." + nau.getId_nave());
//            if (nau.getId_nave() == 9) {
//                posXprimeraNau = 180;
//                posYprimeraNau += 140;
//            }
//        }
           // System.out.println(nau.getId_nave()+  "   size : " + marcianoNaves.size());



        for (int y = posYprimeraNau, i = 0; y < 1250 && i < 3; y += incrementPosNauY, i++) {
            for (int x = posXprimeraNau, e = 0; x < 1700 && e < cantidadNaves; x += incrementPosNauX, e++) {

                if (marcianoNaves.get(i + e) != null) {
                    marcianoNaves.get(i + e).setPosition(x, y);
                    marcianoNaves.get(i + e).render(gc);
                }
//                    if (marcianoNaves.get(i + e).getId_nave() != 100) {
//                        marcianoNaves.get(i + e).setPosition(x, y);
//                        marcianoNaves.get(i + e).render(gc);
//                if (marcianoNaves[i][e] != null) {
//                    System.out.println("entro valor i: " + i);
//                    marcianoNaves[i][e].setPosition(x, y);
                //gc.drawImage(new Image("images/m61.png",75,35,false,false), x, y);
//                if (marcianoNaves[i][e] != null){
//                gc.drawImage(marcianoNaves[i][e].getImage(),x,y);
            }


        }
    }
//        for ( Sprite nau: marcianoNaves){
//            nau.setPosition(nau.moveX(), nau.moveY(marcianoNaves));
//            nau.render(gc);
//           // System.out.println(nau.getId_nave()+  "   size : " + marcianoNaves.size());
//
//        }

    private void renderitzarMisil() {
        int pos = misil.size()-1;

        for (int i = 0; i < misil.size() ; i++) {
            misil.get(i).setPosition( misil.get(i).getPosX(), misil.get(i).move());
            if ( misil.get(pos).getPosY() < posicionUltimoDisparo){
                misilRetarder = true;
            }
            misil.get(i).render(gc);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPaneFondo.setPrefHeight(Mides.APP_HEIGHT);;
        anchorPaneFondo.setPrefWidth(Mides.APP_WIDTH);
        canvas.setWidth(Mides.APP_WIDTH);
        canvas.setHeight(Mides.APP_HEIGHT);

        imageFondo = new Image("images/fondo.jpg", Mides.APP_WIDTH, Mides.APP_HEIGHT,false, false);
        fondoPantalla = new ImageView(imageFondo);
        player = new Player(new Image("images/nau.png", 120,60,false,false));
        crearMarciano();
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("OCR A Std", 30));
        gc.fillRect(100,100,100,100);
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
                //if (misil.size() < limiteDiparos) {
                if (event.getCode() == KeyCode.SPACE) {
                    disparar();
                }
                // }
            }
        });
    }
    public void setScene(Scene sc) {
        scene = sc;
    }

    //    private void setMovedEnemies() {
//        for (int y = 80, i = 0; y < APP_HEIGHT / 2 + SPACE && i < 5; y += SPACE, i++) {
//            for (int x = APP_WIDTH/3 - (SPACE*3), j = 0; x < 660 && j < 13; x += SPACE, j++) {
//                if (y < 90) {
//                    enemiesMoved[i][j] = spawnAlien(x, y, "/images/small_invader_b.png");
//                } else if (y < 200) {
//                    enemiesMoved[i][j] = spawnAlien(x, y, "/images/medium_invader_b.png");
//                } else {
//                    enemiesMoved[i][j] = spawnAlien(x, y, "/images/large_invader_b.png");
//                }
//            }
//        }
//    }
    public void crearMarciano(){
        marcianoNaves = new ArrayList<>();
        String valorParaId;
        int id ;
        for (int y = 165, i= 0 ; y < 450 && i < 3 ; y += 120, i++) {
            for (int x = 180, e = 0; x < 1700 && e < cantidadNaves; x += 140, e++) {
                valorParaId = String.valueOf(i)+String.valueOf(e);
                id = Integer.parseInt(valorParaId);
                Sprite marciano = new Sprite();
                marciano.setImage(new Image("images/ufo.png",75,35,false,false));
                marciano.setPosition(x,y);
                marciano.setId_nave(id);
                marcianoNaves.add(marciano);
                System.out.println("id:   ............................. "+ i+e);
                totalEnemics++;
            }
        }
    }
    //    private Sprite spawnAlien(int x, int y, String imagePath) {
//        Sprite smallAlien = new Sprite();
//        smallAlien.setImage(new Image(imagePath,75,35, false,false));
//        smallAlien.setPosition(x, y);
//        return smallAlien;
//    }
    public void disparar() {
        Sprite disparos = new Sprite();
        disparos.setImage(new Image("images/projectil.png", 15,15,false, false));
        disparos.setPosition(player.getPosX() + 50, player.getPosY() +20 );

        if ( misilRetarder ) {
            misil.add(disparos);
            misilRetarder = false;
            MediaPlayer laserClip = new MediaPlayer(laser);
            laserClip.play();
        }

    }
}