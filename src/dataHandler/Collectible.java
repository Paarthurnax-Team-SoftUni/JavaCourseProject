package dataHandler;

import java.util.Random;

public class Collectible {
    public static Sprite generateCollectible() {

        Random collectibleX = new Random();
        long numb = System.currentTimeMillis() % 3;
        //TODO: change stringDirectory to the correct images!
        String stringDirectory = Constants.COLLECTIBLES_PATH + (numb + 1) + ".png";

        Sprite collectible = new Sprite();
        collectible.setName(String.valueOf(numb + 1));
        collectible.setImage(stringDirectory);
        collectible.setPosition(50 + collectibleX.nextInt(300), -60);

        return collectible;
    }

}
