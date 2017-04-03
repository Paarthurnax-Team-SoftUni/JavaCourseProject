package dataHandler;

import models.Sprite;
import constants.CarConstants;

import java.util.Random;

public class GetRandomToken extends Sprite {

    private String imagesDirectory;

    public GetRandomToken( String[] tokens) {
        setImagesDirectory(tokens);
    }

    protected String getImagesDirectory() {
        return this.imagesDirectory;
    }

    private void setImagesDirectory(String[] tokens) {

        String random = (tokens[new Random().nextInt(tokens.length)]);
        this.imagesDirectory = CarConstants.IMAGES_PATH + random + ".png";

    }
}
