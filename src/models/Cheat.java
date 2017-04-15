package models;

import utils.constants.GameplayConstants;

import java.util.ArrayDeque;
import java.util.Optional;

public class Cheat {

    private ArrayDeque<String> cheat;

    public Cheat() {
        this.cheat = new ArrayDeque<>();
    }

    public void add(String s) {
        this.cheat.add(s);
        if (this.cheat.size() > GameplayConstants.CHEAT_STRING.length()) {
            this.cheat.pollFirst();
        }
    }

    public void useCheat(Player player) {
        switch (getCheatNumber()) {
            case 1:
                player.getCar().setAmmunition(GameplayConstants.CHEAT_BULLETS_COUNT);
                break;
            case -1:
                break;
        }
    }

    private int getCheatNumber() {
        Optional<String> reduce = this.cheat.stream().reduce(String::concat);
        if (reduce.isPresent()) {
            String bufferString = reduce.get();
            if (bufferString.contains(GameplayConstants.CHEAT_STRING)) {
                return 1;
            }
        }
        return -1;
    }
}
