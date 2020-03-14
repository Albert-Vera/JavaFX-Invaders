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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Duration;



import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    private Scene scene;
    private GraphicsContext gc;
    Player player;
    Marciano marciano;
    Projectil projectil;

    FondoPantalla fondo;
    private Pane root = new Pane();
    public Sprite play = new Sprite(300, 750, 40, 40, "player", Color.BLUE);

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
            marciano.clear(gc);
            marciano.move(scene);
            projectil.render(gc);
//            gc.setFill(Color.BLACK);
//       gc.fillRect(0, 0, 2100, 1500);
            player.render(gc);
            // fondo.render(gc);
            marciano.render(gc);
        }
    })
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image imageFondo = new Image("images/fondo.jpg", 4000, 2700,false, false);

        fondoPantalla.setImage(imageFondo);


        //fondo = new FondoPantalla(new Image("images/fondo.jpg"));
        player = new Player(new Image("images/player.png"));
        marciano = new Marciano(new Image("images/ufo.png",false));
        projectil = new Projectil(new Image("images/projectil.png", 15,15,false, false));

        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("OCR A Std", 30));
        gc.fillText("SCORE<1>\t\t\t\t\t\t    HI-SCORE\t\t\t\t\t\t\t\t  SCORE<2>", 30, 30);
//        gc.fillText("" + score + "            9990   ", 30, 60);
//        gc.fillText("mensaje", 300, 400);
//        gc.fillText("" + 3 + "                   CREDIT " + 1, 30, 680);
//        gc.setFill(Color.GREEN);
//        gc.fillRect(30, 650, 640, 4);

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void setScene(Scene sc) {
        scene = sc;
    }



//    private boolean collide(Sprite sprite){
//        if(spriteFrame.getBoundsInParent().intersects(sprite.spriteFrame.getBoundsInParent())){
//            Shape intersection = SVGPath.intersect(spriteBound, sprite.spriteBound);
//            if(!intersection.getBoundsInLocal().isEmpty()){
//                return true;
//            }
//        }
//        return false;
//    }

    private static class Sprite extends Rectangle {
        boolean dead = false;
        final String type;

        Sprite(int x, int y, int w, int h, String type, Color color) {
            super(w, h, color);

            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - 5);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 5);
        }

        void moveUp() {
            setTranslateY(getTranslateY() - 5);
        }

        void moveDown() {
            setTranslateY(getTranslateY() + 5);
        }
    }
}
