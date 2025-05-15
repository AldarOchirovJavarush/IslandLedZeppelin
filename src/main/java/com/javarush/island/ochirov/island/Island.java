package com.javarush.island.ochirov.island;

import lombok.Getter;

import java.util.Optional;

public class Island {
    @Getter
    private final int width;
    @Getter
    private final int height;
    private final Cell[][] cells;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        initializeCells();
    }

    private void initializeCells() {
        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                cells[x][y].updateNeighbours(this::getCell);
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
