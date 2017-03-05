package models;

import dataHandler.Constants;

import java.util.Random;

public class Collectible extends Sprite {



    public static Collectible generateCollectible() {

        String[] collectales=Constants.COLLECTABLE_LIST_SMALL;
        String random=collectales[new Random().nextInt(collectales.length)];

        Random collectibleX = new Random();
        String stringDirectory = Constants.IMAGES_PATH + random + ".png";

        Collectible collectible = new Collectible();
        collectible.setName(random);
        collectible.setImage(stringDirectory);
        collectible.setPosition(50 + collectibleX.nextInt(300), -60);

        return collectible;
    }

    public String getCollectibleType(){
        switch (this.getName()){
            case "collectable1_half_size":
                return "fuelBottle";
            case "collectable2_half_size":
                return "health";
            case "collectable3_half_size":
                return "bonusPts";
            case "collectable4_half_size":
                return "immortality";
            case "collectable5_half_size":
                return "armageddonsPower";
        }
        return "bonusPts";
    }

}
