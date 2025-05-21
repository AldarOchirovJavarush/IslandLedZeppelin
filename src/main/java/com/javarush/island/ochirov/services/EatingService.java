package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismPool;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.utils.Randomizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EatingService extends AbstractOrganismService {
    @Override
    public void action(Animal animal) {
        var currentCell = animal.getCurrentCell();
        List<Organism> potentialPrey = new ArrayList<>(currentCell.getOrganisms());
        potentialPrey.sort(Comparator.comparingInt(Organism::getId));

        for (var prey : potentialPrey) {
            if (prey == animal) continue;

            var first = animal.getId() < prey.getId() ? animal : prey;
            var second = animal.getId() < prey.getId() ? prey : animal;

            try {
                first.lock();
                second.lock();
                try {
                    if (canEat(animal, prey) && currentCell.contains(prey)) {
                        animal.increaseCurrentSafety(prey.getConfig().weight());
                        var eatLog = animal.getConfig().displaySymbol() + animal.getId() + " in "
                                + currentCell.getX() + " " + currentCell.getY()
                                + " eated " + prey.getConfig().displaySymbol() + prey.getId();
                        currentCell.removeOrganism(prey);
                        OrganismPool.release(prey);
                        ConsoleOutputManager.printWithLock(eatLog);
                    }
                } finally {
                    second.unlock();
                }
            } finally {
                first.unlock();
            }
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
