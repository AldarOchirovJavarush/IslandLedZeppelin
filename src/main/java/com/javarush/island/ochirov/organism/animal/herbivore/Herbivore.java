package com.javarush.island.ochirov.organism.animal.herbivore;

import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.animal.Animal;
import com.javarush.island.ochirov.services.AbstractOrganismService;

public abstract class Herbivore extends Animal {
    public Herbivore(OrganismConfig config,
                     AbstractOrganismService movementService,
                     AbstractOrganismService eatingService,
                     AbstractOrganismService deathService,
                     AbstractOrganismService reproduceService) {
        super(config, movementService, eatingService, deathService, reproduceService);
    }
}
