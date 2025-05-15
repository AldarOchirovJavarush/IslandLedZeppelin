package com.javarush.island.ochirov.island;


import com.javarush.island.ochirov.organism.Organism;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    private final int x, y;
    private final Queue<Organism> organisms = new ConcurrentLinkedQueue<>();
    private final Lock lock = new ReentrantLock(true);

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean addOrganism(Organism organism) {
        var config = organism.getConfig();

        lock.lock();
        try {
            long currentCount = organisms.stream().filter(o -> o.getConfig().getKey().equals(config.getKey())).count();

            if (currentCount >= config.getMaxPerCell()) {
                return false;
            }
            return organisms.add(organism);
        } finally {
            lock.unlock();
        }
    }

    public boolean removeOrganism(Organism organism) {
        lock.lock();
        try {
            return organisms.remove(organism);
        } finally {
            lock.unlock();
        }
    }

    public Queue<Organism> getOrganisms() {
        lock.lock();
        try {
            return organisms;
        } finally {
            lock.unlock();
        }
    }
}
