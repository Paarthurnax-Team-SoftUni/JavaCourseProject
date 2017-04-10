package models.sprites;

import constants.GameplayConstants;

public abstract class DestroyableSprite extends CollectibleSprite {

    private boolean isDestroyed;

    protected DestroyableSprite() {
    }

    protected DestroyableSprite(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public void removeWind(){
        super.setVelocity(GameplayConstants.CANVAS_BEGINNING, GameplayConstants.CANVAS_BEGINNING);
        super.setAngle(0);
        super.setTurnRight(false);
        super.setTurnLeft(false);
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

}
