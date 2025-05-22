package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.utils.Randomizer;

public class MovementService extends AbstractAnimalService {
    @Override
    public void animalAction(Animal animal) {
        var movingCellsCount = Randomizer.getRandom(0, animal.getConfig().speed() + 1);
        if (movingCellsCount == 0) {
            return;
        }

        for (var i = 0; i < movingCellsCount; i++) {
            tryMove(animal);
        }
    }

    private void tryMove(Animal animal) {
        var currentCell = animal.getCurrentCell();
        var neighbours = currentCell.getNeighbours();

        for (var nextCell : neighbours) {
            if (attemptMove(animal, currentCell, nextCell)) {
                break;
            }
        }
    }

    private boolean attemptMove(Animal animal, Cell currentCell, Cell nextCell) {
        var first = currentCell.getId() < nextCell.getId() ? currentCell : nextCell;
        var second = currentCell.getId() < nextCell.getId() ? nextCell : currentCell;

        try {
            first.lock();
            second.lock();
            try {
                if (validateMoveConditions(animal, currentCell, nextCell)) {
                    executeMove(animal, currentCell, nextCell);
                    return true;
                }
            } finally {
                second.unlock();
            }
        } finally {
            first.unlock();
        }
        return false;
    }

    private boolean validateMoveConditions(Animal animal, Cell currentCell, Cell nextCell) {
        return currentCell.contains(animal) && !nextCell.isOrganismsOverflow(animal);
    }

    private void executeMove(Animal animal, Cell from, Cell to) {
        to.addOrganism(animal);
        from.removeOrganism(animal);
        animal.setCurrentCell(to);

        var log = String.format("%s%d moved from (%d,%d) to (%d,%d)",
                animal.getConfig().displaySymbol(),
                animal.getId(),
                from.getX(), from.getY(),
                to.getX(), to.getY());

        ConsoleOutputManager.printWithLock(log);
    }
}
