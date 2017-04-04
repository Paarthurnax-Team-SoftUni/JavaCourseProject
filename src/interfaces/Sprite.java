package interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public interface Sprite {
    void update();

    void update(int min, int max);

    void render(GraphicsContext gc);

    void updateImage(String filename);

    Image getImage();


}
