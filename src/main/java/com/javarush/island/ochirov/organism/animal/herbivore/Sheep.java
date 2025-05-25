package com.javarush.island.ochirov.organism.animal.herbivore;

import com.javarush.island.ochirov.configs.records.OrganismConfig;
import com.javarush.island.ochirov.configs.utils.DefaultOrganismConfigFactory;
import com.javarush.island.ochirov.organism.RegisteredOrganism;
import com.javarush.island.ochirov.services.AbstractOrganismService;

@RegisteredOrganism(DefaultOrganismConfigFactory.OrganismType.SHEEP)
public class Sheep extends Herbivore {
    public Sheep(OrganismConfig config, AbstractOrganismService movementService, AbstractOrganismService eatingService, AbstractOrganismService deathService, AbstractOrganismService reproduceService) {
        super(config, movementService, eatingService, deathService, reproduceService);
    }
}
