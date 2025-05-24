package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.consts.StringMessages;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.utils.OrganismPool;
import com.javarush.island.ochirov.utils.Randomizer;

public class ReproduceService extends AbstractOrganismService {
    public ReproduceService(boolean shouldLog) {
        super(shouldLog);
    }

    @Override
    public void action(Organism organism) {
        var cell = organism.getCurrentCell();
        cell.lock();
        try {
            if (canReproduceInCell(organism, cell)) {
                reproduce(organism, cell);
            }
        } finally {
            cell.unlock();
        }
    }

    private boolean canReproduceInCell(Organism organism, Cell cell) {
        return cell.contains(organism)
                && cell.canAdd(organism)
                && Randomizer.toss(organism.getConfig().reproduceProbability());
    }

    private void reproduce(Organism parent, Cell cell) {
        var child = OrganismPool.acquire(parent.getConfig().key());
        cell.addOrganism(child);
        child.setCurrentCell(cell);
        logReproduction(parent, child, cell);
    }

    private void logReproduction(Organism parent, Organism child, Cell cell) {
        var logMessage = String.format(
                StringMessages.REPRODUCE_MESSAGE,
                parent.getConfig().displaySymbol(),
                parent.getId(),
                child.getConfig().displaySymbol(),
                child.getId(),
                cell.getX(),
                cell.getY()
        );
        log(logMessage);
    }
}
