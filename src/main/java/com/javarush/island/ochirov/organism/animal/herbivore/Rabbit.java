package com.javarush.island.ochirov.organism.animal.herbivore;

import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.RegisteredOrganism;
import com.javarush.island.ochirov.services.AbstractOrganismService;

@RegisteredOrganism(configKey = "rabbit")
public class Rabbit extends Herbivore {
    public Rabbit(OrganismConfig config,
                  AbstractOrganismService movementService,
                  AbstractOrganismService eatingService) {
        super(config, movementService, eatingService);
    }
}
