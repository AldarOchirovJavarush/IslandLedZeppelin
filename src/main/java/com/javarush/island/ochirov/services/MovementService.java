package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.utils.Randomizer;

public class MovementService extends AbstractOrganismService {
    @Override
    public void action(Animal animal) {
        animal.lock();
        try {
            var movingCellsCount = Randomizer.getRandom(0, animal.getConfig().speed() + 1);
            for (var i = 0; i < movingCellsCount; i++) {
                var currentCell = animal.getCurrentCell();
                var neighbours = currentCell.getNeighbours();
                var nextCell = neighbours.get(Randomizer.getRandom(0, neighbours.size()));
                if (nextCell.addOrganism(animal)) {
                    String moveLog = animal.getConfig().displaySymbol() + animal.getId() + " moved from " + currentCell.getX() + " " + currentCell.getY()
                            + " to " + nextCell.getX() + " " + nextCell.getY();
                    ConsoleOutputManager.printWithLock(moveLog);
                    currentCell.removeOrganism(animal);
                    animal.setCurrentCell(nextCell);
                }
            }
        } finally {
            animal.unlock();
        }
    }
}
