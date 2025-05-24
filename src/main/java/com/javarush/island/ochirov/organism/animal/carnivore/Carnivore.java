package com.javarush.island.ochirov.organism.animal.carnivore;

import com.javarush.island.ochirov.configs.records.OrganismConfig;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.services.AbstractOrganismService;

public abstract class Carnivore extends Animal {
    public Carnivore(OrganismConfig config,
                     AbstractOrganismService movementService,
                     AbstractOrganismService eatingService,
                     AbstractOrganismService deathService,
                     AbstractOrganismService reproduceService) {
        super(config, movementService, eatingService, deathService, reproduceService);
    }
}
