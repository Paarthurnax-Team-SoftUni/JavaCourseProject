package models;

import javafx.scene.canvas.GraphicsContext;

public interface Sprite {
    void update();

    void update(int min, int max);

    void render(GraphicsContext gc);
}
