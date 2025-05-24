package com.javarush.island.ochirov.island;

import com.javarush.island.ochirov.organism.Organism;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Cell {
    @Getter
    private final int x;
    @Getter
    private final int y;
    @Getter
    private final long id;
    @Getter
    private final Lock lock = new ReentrantLock(true);
    private final List<Cell> neighbours = new ArrayList<>();
    private final Queue<Organism> organisms = new ConcurrentLinkedQueue<>();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        id = ((long) x << 32) | (y & 0xFFFFFFFFL);
    }

    public void updateNeighbours(BiFunction<Integer, Integer, Optional<Cell>> getCellFunc) {
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (var dir : directions) {
            var nx = x + dir[0];
            var ny = y + dir[1];
            getCellFunc.apply(nx, ny).ifPresent(neighbours::add);
        }

        neighbours.sort(Comparator.comparingLong(c -> c.id));
    }

    public List<Cell> getNeighbours() {
        return Collections.unmodifiableList(neighbours);
    }

    private <T> T executeWithLock(Supplier<T> action) {
        var lockedHere = false;
        try {
            if (!isLockedByCurrentThread()) {
                lock.lock();
                lockedHere = true;
            }
            return action.get();
        } finally {
            if (lockedHere) {
                lock.unlock();
            }
        }
    }

    public void addOrganism(Organism organism) {
        executeWithLock(() -> organisms.add(organism));
    }

    public void removeOrganism(Organism organism) {
        executeWithLock(() -> organisms.remove(organism));
    }

    public List<Organism> getOrganisms() {
        return executeWithLock(() -> new ArrayList<>(organisms));
    }

    public boolean contains(Organism organism) {
        return organisms.contains(organism);
    }

    public boolean canAdd(Organism organism) {
        var config = organism.getConfig();
        var currentCount = organisms.stream()
                .filter(o -> o.getConfig().key().equals(config.key()))
                .count();
        return currentCount < config.maxPerCell();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    private boolean isLockedByCurrentThread() {
        return ((ReentrantLock) lock).isHeldByCurrentThread();
    }
}
