package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.consts.StringMessages;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismPool;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.utils.Randomizer;

import java.util.Comparator;
import java.util.List;

public class EatingService extends AbstractAnimalService {
    public EatingService(boolean shouldLog) {
        super(shouldLog);
    }

    @Override
    public void animalAction(Animal animal) {
        var currentCell = animal.getCurrentCell();
        var predatorKey = animal.getConfig().key();
        List<Organism> potentialPrey = currentCell.getOrganisms().stream()
                .filter(prey -> !prey.getConfig().key().equals(predatorKey))
                .sorted(Comparator.comparingInt(Organism::getId))
                .toList();

        for (var prey : potentialPrey) {
            var first = animal.getId() < prey.getId() ? animal : prey;
            var second = animal.getId() < prey.getId() ? prey : animal;

            try {
                first.lock();
                second.lock();
                try {
                    if (canEat(animal, prey) && currentCell.contains(prey) && prey.isAlive()) {
                        animal.increaseCurrentSafety(prey.getConfig().weight());
                        logEating(animal, prey, currentCell);
                        currentCell.removeOrganism(prey);
                        OrganismPool.release(prey);
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

    private void logEating(Animal animal, Organism prey, Cell currentCell) {
        var logMessage = String.format(
                StringMessages.EATING_MESSAGE,
                animal.getConfig().displaySymbol(),
                animal.getId(),
                prey.getConfig().displaySymbol(),
                prey.getId(),
                currentCell.getX(),
                currentCell.getY()
        );
        log(logMessage);
    }
}
