package com.javarush.island.ochirov.organism.animal;

import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.behavior.Eater;
import com.javarush.island.ochirov.organism.behavior.Movable;
import com.javarush.island.ochirov.services.AbstractOrganismService;
import lombok.Getter;

public abstract class Animal extends Organism implements Movable, Eater {
    @Getter
    protected double currentSafety;
    @Getter
    protected double currentHunger;
    private final AbstractOrganismService movementService;
    private final AbstractOrganismService eatingService;

    public Animal(OrganismConfig config,
                  AbstractOrganismService movementService,
                  AbstractOrganismService eatingService) {
        super(config);
        currentSafety = config.satiety();
        currentHunger = 0;
        this.movementService = movementService;
        this.eatingService = eatingService;
    }

    private void action(AbstractOrganismService service) {
        lock.lock();
        try {
            service.action(this);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void move() {
        action(movementService);
    }

    @Override
    public void eat() {
        action(eatingService);
    }

    @Override
    public boolean isHungry() {
        return currentSafety < config.satiety();
    }

    @Override
    public void increaseCurrentSafety(double value) {
        currentSafety += value;
        currentHunger -= value;
    }

    @Override
    public void decreaseCurrentSafety() {
        currentSafety -= config.starvation();
        currentHunger += config.starvation();
    }
}
