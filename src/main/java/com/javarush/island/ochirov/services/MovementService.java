package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.ConsoleOutputManager;
import com.javarush.island.ochirov.organism.animal.Animal;

import java.util.concurrent.ThreadLocalRandom;

public class MovementService {
    public boolean move(Animal animal) {
        animal.lock();
        try {
            var currentCell = animal.getCurrentCell();
            var neighbours = currentCell.getNeighbours();
            var nextCell = neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));
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
