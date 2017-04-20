package models.sprites.collectibles;

import annotations.Collectable;
import interfaces.Randomizer;

@Collectable
public class Armagedon extends Collectible {

    private static final String ARMAGEDON_NAME = "armageddonPower";
    private static final String ARMAGEDON_NOTIFICATION_MESSAGE = "Armageddons Power! Nothing can get on your way now";
    private static final int ARMAGEDON_BONUS = 500;

    public Armagedon(Randomizer randomizer) {
        super(randomizer);
        this.setProps();
    }

    private void setProps() {
        this.updateName(ARMAGEDON_NAME);
        this.setNotificationMessage(ARMAGEDON_NOTIFICATION_MESSAGE);
        this.setBonusPoints(ARMAGEDON_BONUS);
    }
}
