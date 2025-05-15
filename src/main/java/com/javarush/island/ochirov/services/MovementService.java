package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.ConsoleOutputManager;
import com.javarush.island.ochirov.island.Island;
import com.javarush.island.ochirov.organism.animal.Animal;

import java.util.concurrent.ThreadLocalRandom;

public class MovementService {
    private final AdjacentCellService adjacentCellService = new AdjacentCellService();

    public boolean move(Island island, Animal animal) {
        animal.lock();
        try {
            var currentCell = animal.getCurrentCell();
            var adjacentCells = adjacentCellService.getAdjacentCells(island, currentCell.getX(), currentCell.getY());
            var nextCell = adjacentCells.get(ThreadLocalRandom.current().nextInt(adjacentCells.size()));
            if (nextCell.addOrganism(animal)) {
                String moveLog = animal.getConfig().displaySymbol() + animal.getId() + " moved from " + currentCell.getX() + " " + currentCell.getY()
                        + " to " + nextCell.getX() + " " + nextCell.getY();
                ConsoleOutputManager.printWithLock(moveLog);
                currentCell.removeOrganism(animal);
                animal.setCurrentCell(nextCell);
            }
        } finally {
            animal.unlock();
        }

        return true;
    }
}
