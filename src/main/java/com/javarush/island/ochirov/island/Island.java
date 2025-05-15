package com.javarush.island.ochirov.island;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Island {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final Lock lock = new ReentrantLock();

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        initializeCells();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void initializeCells() {
        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public Optional<Cell> getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return Optional.of(cells[x][y]);
        }
        return Optional.empty();
    }
}
