package com.javarush.island.ochirov.organism.animal;

import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.behavior.Movable;
import com.javarush.island.ochirov.services.MovementService;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal extends Organism implements Movable {
    private final Lock moveLock = new ReentrantLock();
    private final MovementService movementService = new MovementService();

    public Animal(OrganismConfig config) {
        super(config);
    }

    @Override
    public boolean move() {
        moveLock.lock();
        try {
            return movementService.move(this);
        } finally {
            moveLock.unlock();
        }
    }

    public void lock() {
        moveLock.lock();
    }

    public void unlock() {
        moveLock.unlock();
    }
}
