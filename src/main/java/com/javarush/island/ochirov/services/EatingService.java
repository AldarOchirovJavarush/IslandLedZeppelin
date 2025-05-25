package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.consts.StringMessages;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.utils.OrganismPool;
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

        potentialPrey.forEach(prey -> tryEat(animal, prey, currentCell));
    }

    private void tryEat(Animal predator, Organism prey, Cell cell) {
        var first = predator.getId() < prey.getId() ? predator : prey;
        var second = predator.getId() < prey.getId() ? prey : predator;
        try {
            first.lock();
            second.lock();
            try {
                if (canEat(predator, prey) && cell.contains(prey) && prey.isAlive()) {
                    predator.increaseCurrentSafety(prey.getConfig().weight());
                    logEating(predator, prey, cell);
                    cell.removeOrganism(prey);
                    OrganismPool.release(prey);
                }
            } finally {
                second.unlock();
            }
        } finally {
            first.unlock();
        }
    }

    private boolean canEat(Animal animal, Organism prey) {
        var preyKey = prey.getConfig().key();
        var animalEatProbabilities = animal.getConfig().eatProbabilities();

        return animalEatProbabilities.containsKey(preyKey)
                && animal.getCurrentHunger() > 0
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
