package com.javarush.island.ochirov.organism.plant;

import com.javarush.island.ochirov.configs.records.OrganismConfig;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.services.AbstractOrganismService;

public abstract class Plant extends Organism {
    public Plant(OrganismConfig config, AbstractOrganismService deathService, AbstractOrganismService reproduceService) {
        super(config, deathService, reproduceService);
    }
}
