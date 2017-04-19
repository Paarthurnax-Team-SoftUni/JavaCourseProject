package models.sprites.collectibles;

import utils.RandomProvider;

public class ArmagedonPower extends Collectible{

    private static final String ARMAGEDON_NAME = "armageddonsPower";
    private static final String ARMAGEDON_NOTIFICATION_MESSAGE = "Armageddons Power! Nothing can get on your way now";
    private static final int ARMAGEDON_BONUS = 500;

    public ArmagedonPower(RandomProvider randomProvider) {
        super(randomProvider);
        this.setProps();
    }

    private void setProps(){
        this.setName(ARMAGEDON_NAME);
        this.setNotificationMessage(ARMAGEDON_NOTIFICATION_MESSAGE);
        this.setBonusPoints(ARMAGEDON_BONUS);
    }
}
