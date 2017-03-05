package models.interfaces;

import dataHandler.Constants;
import models.Collectible;

import java.util.Random;

public interface CollectibleInterface {
    String getCollectibleType();

    static Collectible generateCollectible() {

        String[] collectales= Constants.COLLECTABLE_LIST_SMALL;
        String random=collectales[new Random().nextInt(collectales.length)];

        Random collectibleX = new Random();
        String stringDirectory = Constants.IMAGES_PATH + random + ".png";

        Collectible collectible = new Collectible();
        collectible.setName(random);
        collectible.setImage(stringDirectory);
        collectible.setPosition(50 + collectibleX.nextInt(300), -60);

        return collectible;
    }
}
