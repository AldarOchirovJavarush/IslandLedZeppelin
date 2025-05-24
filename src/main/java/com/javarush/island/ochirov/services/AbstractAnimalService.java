package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.animal.Animal;

public abstract class AbstractAnimalService extends AbstractOrganismService {
    protected AbstractAnimalService(boolean shouldLog) {
        super(shouldLog);
    }

    @Override
    public void action(Organism organism) {
        if (organism instanceof Animal animal) {
            animalAction(animal);
        }
    }

    protected abstract void animalAction(Animal animal);
}
