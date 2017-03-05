package models;

import dataHandler.Constants;

import java.util.Random;

public class Collectible extends Sprite {

    public String getCollectibleType(){
        switch (this.getName()){
            case "collectable1_half_size":
                return "fuelBottle";
            case "collectable2_half_size":
                return "health";
            case "collectable3_half_size":
                return "doublePts";
            case "collectable4_half_size":
                return "immortality";
            case "collectable5_half_size":
                return "armageddonsPower";
        }
        return "bonusPts";
    }

}
