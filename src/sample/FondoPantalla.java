package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FondoPantalla {
    private Image image;
    private double width, height, posX, posY;
    public FondoPantalla(Image image) {
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

}
