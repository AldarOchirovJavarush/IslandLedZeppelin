package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.island.Island;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdjacentCellService {

    public List<Cell> getAdjacentCells(Island island, int x, int y) {
        List<Cell> adjacentCells = new ArrayList<>();

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (var dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            island.getCell(nx, ny).ifPresent(adjacentCells::add);
        }

        return Collections.unmodifiableList(adjacentCells);
    }
}
