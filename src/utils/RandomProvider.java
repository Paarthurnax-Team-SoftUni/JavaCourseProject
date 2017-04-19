package utils;

import java.util.Random;

public class RandomProvider {
    private Random random;

    public RandomProvider() {
        this.random = new Random();
    }

    public int next(int max) {
        return this.random.nextInt(max);
    }
}
