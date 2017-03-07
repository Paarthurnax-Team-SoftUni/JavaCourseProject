package gameEngine;

import models.Player;

import java.util.ArrayDeque;
import java.util.Optional;

public class Cheat {
    private ArrayDeque<String> cheat;

    public void add(String s) {
        this.cheat.add(s);
        if (this.cheat.size() > 5) {
            this.cheat.pollFirst();
        }
    }

    private int getCheatNumber() {
        Optional<String> reduce = this.cheat.stream().reduce(String::concat);
        if (reduce.isPresent()) {
            String bufferString = reduce.get();
            if (bufferString.contains("IDKFA")) {
                return 1;
            }
        }
        return -1;
    }

    public Cheat() {
        this.cheat = new ArrayDeque<>();
    }

    public void useCheat(Player player) {
        switch (getCheatNumber()) {
            case 1:
                player.setAmmunition(1000);
                break;
            case -1:
                break;
        }
    }
}
