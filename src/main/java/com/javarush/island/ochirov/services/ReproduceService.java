package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismPool;
import com.javarush.island.ochirov.utils.Randomizer;

public class ReproduceService extends AbstractOrganismService {
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
                && !cell.isOrganismsOverflow(organism)
                && Randomizer.toss(organism.getConfig().reproduceProbability());
    }

    private void reproduce(Organism parent, Cell cell) {
        var newOrganism = OrganismPool.acquire(parent.getConfig().key());
        cell.addOrganism(newOrganism);
        newOrganism.setCurrentCell(cell);
        logReproduction(parent, newOrganism, cell);
    }

    private void logReproduction(Organism parent, Organism offspring, Cell cell) {
        String log = String.format("%s%d budded at (%d,%d). New: %s%d",
                parent.getConfig().displaySymbol(), parent.getId(),
                cell.getX(), cell.getY(),
                offspring.getConfig().displaySymbol(), offspring.getId());
        ConsoleOutputManager.printWithLock(log);
    }
}
