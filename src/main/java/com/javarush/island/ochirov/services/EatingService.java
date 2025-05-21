package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismPool;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.utils.Randomizer;

public class EatingService extends AbstractOrganismService {
    @Override
    public void action(Animal animal) {
        animal.lock();
        try {
            if (animal.isHungry()) {
                var currentCell = animal.getCurrentCell();
                var organismsInCell = currentCell.getOrganisms();
                for (var prey : organismsInCell) {
                    if (canEat(animal, prey)) {
                        animal.increaseCurrentSafety(prey.getConfig().weight());
                        var eatLog = animal.getConfig().displaySymbol() + animal.getId() + " in "
                                + currentCell.getX() + " " + currentCell.getY()
                                + " eated " + prey.getConfig().displaySymbol() + prey.getId();
                        currentCell.removeOrganism(prey);
                        OrganismPool.release(prey);
                        ConsoleOutputManager.printWithLock(eatLog);
                    }
                }
            }
        } finally {
            animal.unlock();
        }
    }

    private boolean canEat(Animal animal, Organism prey) {
        var preyKey = prey.getConfig().key();
        var animalEatProbabilities = animal.getConfig().eatProbabilities();

        return animalEatProbabilities.containsKey(preyKey)
                && animal.getCurrentHunger() > prey.getConfig().weight()
                && Randomizer.toss(animalEatProbabilities.get(preyKey));
    }
}
