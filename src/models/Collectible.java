package models;

import dataHandler.Constants;

import java.util.Random;

public class Collectible extends Sprite {



    public static Collectible generateCollectible() {

        String[] collectales=Constants.COLLECTABLE_LIST;
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
            case "collectable1":
                return "fuelBottle";
            case "collectable2":
                return "health";
            case "collectable3":
                return "bonusPts";
            case "collectable4":
                return "immortality";
            case "collectable5":
                return "armageddonsPower";
        }
        return "bonusPts";
    }

}
