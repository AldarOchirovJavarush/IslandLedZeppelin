package com.javarush.island.ochirov.organism.behavior;

public interface Eater {
    void eat();
    void increaseCurrentSafety(double value);
    void decreaseCurrentSafety();
}
