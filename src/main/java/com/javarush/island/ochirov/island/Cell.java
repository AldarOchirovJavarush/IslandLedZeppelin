package com.javarush.island.ochirov.island;

import com.javarush.island.ochirov.organism.Organism;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

public class Cell {
    @Getter
    private final int x;
    @Getter
    private final int y;
    private final List<Cell> neighbours = new ArrayList<>();
    private final Queue<Organism> organisms = new ConcurrentLinkedQueue<>();
    private final Lock lock = new ReentrantLock(true);

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateNeighbours(BiFunction<Integer, Integer, Optional<Cell>> getCellFunc) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (var dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            getCellFunc.apply(nx, ny).ifPresent(neighbours::add);
        }
    }

    public List<Cell> getNeighbours() {
        return Collections.unmodifiableList(neighbours);
    }

    public boolean addOrganism(Organism organism) {
        var config = organism.getConfig();

        lock.lock();
        try {
            long currentCount = organisms.stream().filter(o -> o.getConfig().key().equals(config.key())).count();

            if (currentCount >= config.maxPerCell()) {
                return false;
            }
            return organisms.add(organism);
        } finally {
            lock.unlock();
        }
    }

    public void removeOrganism(Organism organism) {
        lock.lock();
        try {
            organisms.remove(organism);
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
