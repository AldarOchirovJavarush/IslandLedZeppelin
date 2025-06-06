package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.consts.StringMessages;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.utils.OrganismPool;
import com.javarush.island.ochirov.organism.animal.Animal;

public class DeathService extends AbstractOrganismService {
    public DeathService(boolean shouldLog) {
        super(shouldLog);
    }

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
                logDeath(organism, cell);
                OrganismPool.release(organism);
                organism.setAlive(false);
            }
        } finally {
            cell.unlock();
        }
    }

    private void logDeath(Organism organism, Cell cell) {
        var logMessage = String.format(
                StringMessages.DEATH_MESSAGE,
                organism.getConfig().displaySymbol(),
                organism.getId(),
                cell.getX(),
                cell.getY()
        );
        log(logMessage);
    }
}
