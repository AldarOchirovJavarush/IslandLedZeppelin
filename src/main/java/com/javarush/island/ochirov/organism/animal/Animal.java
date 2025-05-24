package com.javarush.island.ochirov.organism.animal;

import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.configs.records.OrganismConfig;
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
                  AbstractOrganismService eatingService,
                  AbstractOrganismService deathService,
                  AbstractOrganismService reproduceService) {
        super(config, deathService, reproduceService);
        this.movementService = movementService;
        this.eatingService = eatingService;
        init();
    }

    public void init() {
        super.init();
        currentSafety = config.satiety();
        currentHunger = 0;
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
