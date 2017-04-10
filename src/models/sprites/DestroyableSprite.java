package models.sprites;

public abstract class DestroyableSprite extends CollectibleSprite {

    private boolean isDestroyed;

    public DestroyableSprite() {
    }

    protected DestroyableSprite(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public void removeWind(){
        super.setVelocity(0, 0);
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
