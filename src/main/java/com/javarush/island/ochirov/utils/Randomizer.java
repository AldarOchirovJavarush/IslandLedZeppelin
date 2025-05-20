package com.javarush.island.ochirov.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {
    private Randomizer() {
    }

    public static int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static boolean toss(int percentProbably) {
        return getRandom(0, 100) < percentProbably;
    }
}
