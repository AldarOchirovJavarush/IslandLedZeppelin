package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismPool;
import com.javarush.island.ochirov.organism.animal.Animal;

public class DeathService extends AbstractOrganismService {
    @Override
    public void action(Organism organism) {
        if (shouldDie(organism)) {
            die(organism);
        }
    }

    private boolean shouldDie(Organism organism) {
        if (organism instanceof Animal animal) {
            animal.decreaseCurrentSafety();
            return animal.getCurrentSafety() <= 0;
        }

        return false;
    }

    private void die(Organism organism) {
        var cell = organism.getCurrentCell();
        cell.lock();
        try {
            if (cell.contains(organism)) {
                cell.removeOrganism(organism);
                ConsoleOutputManager.printWithLock(
                        String.format("%s%d died at (%d,%d)",
                                organism.getConfig().displaySymbol(),
                                organism.getId(),
                                cell.getX(), cell.getY())
                );
                OrganismPool.release(organism);
                organism.setAlive(false);
            }
        } finally {
            cell.unlock();
        }
    }
}
