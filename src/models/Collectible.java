package models;

import dataHandler.Constants;

import java.util.Random;

public class Collectible extends Sprite {



    public static Collectible generateCollectible() {

        Random collectibleX = new Random();
        long numb = System.currentTimeMillis() % Constants.COLLECTABLE_LIST.length;
        String stringDirectory = Constants.COLLECTIBLES_PATH + (numb + 1) + ".png";

        Collectible collectible = new Collectible();
        collectible.setName(String.valueOf(numb + 1));
        collectible.setImage(stringDirectory);
        collectible.setPosition(50 + collectibleX.nextInt(300), -60);

        return collectible;
    }

    public String getCollectibleType(){
        switch (this.getName()){
            case "1":
                return "fuelBottle";
            case "2":
                return "health";
            case "3":
                return "bonusPts";
            case "4":
                return "immortality";
            case "5":
                return "armageddonsPower";
        }
        return "bonusPts";
    }

}
