package com.javarush.island.ochirov.organism.animal.carnivore;

import com.javarush.island.ochirov.configs.OrganismConfig;
import com.javarush.island.ochirov.organism.RegisteredOrganism;
import com.javarush.island.ochirov.services.AbstractOrganismService;

@RegisteredOrganism(configKey = "wolf")
public class Wolf extends Carnivore {
    public Wolf(OrganismConfig config,
                AbstractOrganismService movementService,
                AbstractOrganismService eatingService,
                AbstractOrganismService deathService,
                AbstractOrganismService reproduceService) {
        super(config, movementService, eatingService, deathService, reproduceService);
    }
}
