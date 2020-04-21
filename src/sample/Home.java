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
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class Home implements Initializable {
    Mides mides = new Mides();
    private Sprite[][] marcianoNaves = new Sprite[3][10];
    private boolean isFinalPartida, start = false;
    private int jugadorDead = 0;
    private boolean jugadorDeadBool = false;
    private boolean misilRetarder = true;
    ArrayList<Sprite> misil = new ArrayList<>();
    private Scene scene;
    private GraphicsContext gc;
    private Player player;
    private boolean finalPartida = false;
    private String s = getClass().getClassLoader().getResource("sound/soexplosio.wav").toExternalForm();
    private AudioClip sound = new AudioClip(s);
    private final String disparo = getClass().getClassLoader().getResource("sound/disparo2.mp3").toExternalForm();
    private AudioClip laserClip = new AudioClip(disparo);
    private Image imageFondo;
    @FXML ImageView fondoPantalla;
    @FXML AnchorPane anchorJoc;
    @FXML AnchorPane menuInici;
    @FXML AnchorPane anchorPaneFondo;
    @FXML AnchorPane anchorFinal;
    @FXML AnchorPane anchorReset;
    @FXML AnchorPane anchorText;
    @FXML Button btnContinuar;
    @FXML Canvas canvas;
    @FXML Text textScore, textWinner, textExit, text1, text2;

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.217), new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            anchorReset.setVisible(false);
            gc.drawImage(imageFondo, 0, 0);
            if ( jugadorDeadBool ) jugadorDead ++;
            if (jugadorDead == 10) finalPantalla("No ho has aconseguit","Cagada ....");
            if (!finalPartida && start) { // Con boton Start inicia el juego
                anchorText.setVisible(true);
                gc.fillText("SCORE<1> " + mides.score + "\t\t\t\t\t\t    HI-SCORE\t\t\t\t\t\t\t\t  SCORE<2>", 30, 30);
                gc.fillText("Prem 'Q' per abandonar",50,1350);
                if (!jugadorDeadBool) player.render(gc);
                detectarTeclado();
                renderitzarMarciano();
                renderitzarMisil();
                colisiones();
                colisionConNave();
            }
            if (mides.totalEnemics <= 0){
                mides.posXprimeraNau = 180;
                mides.posYprimeraNau = 165;
                mides.increment = 10;
                if (!isFinalPartida) anchorReset.setVisible(true);
                else finalPantalla("Total Score: " + mides.score, "Winner !!!!");
            }
        }
    })
    );
    public void btnContinuar(){
        isFinalPartida = true;
        crearMarciano();
    }
    void finalPantalla(String texte, String texte2){
        anchorPaneFondo.setPrefHeight(Mides.APP_HEIGHT/2);;
        anchorPaneFondo.setPrefWidth(Mides.APP_WIDTH/2);
        anchorJoc.setVisible(false);
        anchorFinal.setVisible(true);
        textWinner.setText(texte2);
        textScore.setText( texte);
    }
    public void clickStart(){
        menuInici.setVisible(false);
        anchorJoc.setVisible(true);
        start = true; // Quan es toca el boto Start per iniciar el joc
    }
    private void clickExit(){ // Quan abandonas la partida
        anchorJoc.setVisible(false);
        anchorFinal.setVisible(true);
        textWinner.setText("Caguetes t'has rendit....");
        textScore.setText("Total Score: " + mides.score);
    }
    private void  colisionConNave(){ // Colisió de la nau del jugador contra els marcians
        for (int i = 0; i < marcianoNaves.length; i++) {
            for (int j = 0; j < marcianoNaves[i].length; j++) {
                if (marcianoNaves[i][j] != null) {
                    if (player != null && player.intersects(marcianoNaves[i][j])) { // si es null es que jugador es dead
                        marcianoNaves[i][j] = null;
                        explosion(player.getPosX()+25, player.getPosY());
                        jugadorDeadBool = true;
                        player = null;
                    }
                }
            }
        }
    }
    private void colisiones() {
        mides.posXprimeraNau += mides.velocitatMarcians;
        if (mides.posXprimeraNau >= mides.limitDretScreen) {
            mides.posYprimeraNau += 50;
            mides.velocitatMarcians = (-mides.aumentVelocitatMarcians);
        }
        if (mides.posXprimeraNau <= 50) {
            mides.posYprimeraNau += 50;
            mides.velocitatMarcians = (+mides.aumentVelocitatMarcians);
        }
        for (int i = 0; i < marcianoNaves.length; i++) {
            for (int j = 0; j < marcianoNaves[i].length; j++) {
                Iterator<Sprite> misilLanzado = misil.iterator();
                while (misilLanzado.hasNext()) {
                    Sprite proyectil = misilLanzado.next();
                    if (marcianoNaves[i][j] != null) {
                        if (proyectil.intersects(marcianoNaves[i][j])) {
                            explosion(marcianoNaves[i][j].getPosX(), marcianoNaves[i][j].getPosY());
                            switch ((int)marcianoNaves[i][j].getWidth()) {
                                case 76: // nau del primer rengle
                                    mides.score += 10;
                                    break;
                                case 75: // nau del segon rengle
                                    mides.score += 20;
                                    break;
                                case 74: // nau del tercer rengle
                                    mides.score += 30;
                                    break;
                            }
                            mides.totalEnemics--;
                            misilLanzado.remove();
                            marcianoNaves[i][j] = null;
                        }
                    }
                }
            }
        }
    }
    private void explosion(double x, double y){
        Sprite sprite = new Sprite();
        sprite.setImage(new Image("images/explosionn.png",105,105,false,false));
        sprite.setPosition(x-20,y-30);
        sprite.render(gc);
        sound.play(25);
    }
    public void renderitzarMarciano() {
        boolean soloPuedePasarUno = false;
        for (int y = mides.posYprimeraNau, i = 0; y < 1250 && i < 3; y += mides.incrementPosNauY, i++) {
            for (int x = mides.posXprimeraNau, e = 0; x < 1700 && e < mides.cantidadNaves; x += mides.incrementPosNauX, e++) {
                if (marcianoNaves[i][e] != null) {
                    marcianoNaves[i][e].setPosition(x, y);
                    gc.drawImage(marcianoNaves[i][e].getImage(), x, y);
                        // Explosió nau jugador i final partida si marcians arriban a baix
                    if ( marcianoNaves[i][e].getPosY() > 1200 && player != null) {
                        explosion(player.getPosX(), player.getPosY());
                        jugadorDeadBool = true;
                        player = null;
                        x -= mides.incrementPosNauX;
                    }
                    // Per aumentar velocitat quan detecti una nau a posY 465 i no aumenti amb totes les naus
                    if (!soloPuedePasarUno) {
                        if (marcianoNaves[i][e].getPosY() == 465) {
                            mides.aumentVelocitatMarcians += mides.increment;
                            soloPuedePasarUno = true;
                            mides.increment = 0;
                        }
                    }
                }
            }
        }
    }
    private void renderitzarMisil() {
        int pos = misil.size()-1;
        for (int i = 0; i < misil.size() ; i++) {
            misil.get(i).setPosition( misil.get(i).getPosX(), misil.get(i).move());
            if ( misil.get(pos).getPosY() < mides.posicionUltimoDisparo){
                misilRetarder = true;
            }
            if (!jugadorDeadBool) misil.get(i).render(gc);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPaneFondo.setPrefHeight(Mides.APP_HEIGHT);;
        anchorPaneFondo.setPrefWidth(Mides.APP_WIDTH);
        canvas.setWidth(Mides.APP_WIDTH);
        canvas.setHeight(Mides.APP_HEIGHT);
        menuInici.setVisible(true);
        anchorReset.setVisible(false);
        anchorText.setVisible(false);
        anchorJoc.setVisible(false);
        anchorFinal.setVisible(false);
        imageFondo = new Image("images/fondo.jpg", Mides.APP_WIDTH, Mides.APP_HEIGHT,false, false);
        fondoPantalla = new ImageView(imageFondo);
        player = new Player(new Image("images/nau.png", 120,60,false,false));
        crearMarciano();
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("OCR A Std", 30));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    void detectarTeclado(){
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.RIGHT) {
                        player.moveRight();
                    }
                    if (event.getCode() == KeyCode.LEFT) {
                        player.moveLeft();
                    }
                    if (event.getCode() == KeyCode.SPACE) {
                        disparar();
                    }
                    if (event.getCode() == KeyCode.Q) clickExit();
                }
            });
    }
    public void setScene(Scene sc) {
        scene = sc;
    }
    public void crearMarciano() {
        for (int y = 165, i = 0; y < 450 && i < 3; y += 120, i++) {
            for (int x = 180, e = 0; x < 1700 && e < mides.cantidadNaves; x += 140, e++) {
                if (y == 165) {
                    marcianoNaves[i][e] = dadesMarciano(x, y, "images/m61.png", 74);
                } else if (y == 285) {
                    marcianoNaves[i][e] = dadesMarciano(x, y, "images/ufo2.png", 75);
                } else {
                    marcianoNaves[i][e] = dadesMarciano(x, y, "images/ufo.png", 76);
                }
                mides.totalEnemics++;
            }
        }
    }
    private Sprite dadesMarciano(int x, int y, String imagePath, double width) {
        Sprite dades = new Sprite();
        dades.setImage(new Image(imagePath,width,35, false,false));
        dades.setPosition(x, y);
        dades.setWidth(width);
        return dades;
    }
    public void disparar() {
        Sprite disparos = new Sprite();
        disparos.setImage(new Image("images/projectil.png", 15,15,false, false));
        disparos.setPosition(player.getPosX() + 50, player.getPosY() +20 );
        if ( misilRetarder ) {
            misil.add(disparos);
            misilRetarder = false;
            laserClip.play(170);
        }
    }
}