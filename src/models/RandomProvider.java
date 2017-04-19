package models;

import interfaces.Randomizer;

import java.util.Random;

public class RandomProvider implements Randomizer {
    private Random random;

    public RandomProvider() {
        this.random = new Random();
    }

    @Override
    public int next(int max) {
        return this.random.nextInt(max);
    }
}
