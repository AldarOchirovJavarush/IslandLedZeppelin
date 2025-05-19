package com.javarush.island.ochirov.organism.animal;

import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.behavior.Movable;
import com.javarush.island.ochirov.services.MovementService;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal extends Organism implements Movable {
    private final Lock lock = new ReentrantLock();
    private final MovementService movementService = new MovementService();

    public Animal(OrganismConfig config) {
        super(config);
    }

    @Override
    public boolean move() {
        lock.lock();
        try {
            return movementService.move(this);
        } finally {
            lock.unlock();
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
