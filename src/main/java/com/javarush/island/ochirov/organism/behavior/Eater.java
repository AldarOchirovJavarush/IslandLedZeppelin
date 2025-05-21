package com.javarush.island.ochirov.organism.behavior;

public interface Eater {
    void eat();
    boolean isHungry();
    void increaseCurrentSafety(double value);
    void decreaseCurrentSafety();
}
