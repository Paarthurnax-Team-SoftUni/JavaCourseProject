package models.interfaces;

import models.Collectible;

public interface CollectibleInterface {
    Collectible generateCollectible();
    String getCollectibleType();
}
