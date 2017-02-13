package DataHandler;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Observable;


    public class CurrentHealth extends Observable {

        private Image image;
        private double positionX;
        private double positionY;


        public CurrentHealth()
        {
            positionX = 0;
            positionY = 0;

        }

        public void setImage(String filename)
        {
            Image i = new Image(filename);
            setImage(i);
        }

        public void setImage(Image i)
        {
            image = i;

        }

        public void setPosition(double x, double y)
        {
            positionX = x;
            positionY = y;
        }
        public void render(GraphicsContext gc)
        {
            gc.drawImage( image, positionX, positionY );
        }



    }

