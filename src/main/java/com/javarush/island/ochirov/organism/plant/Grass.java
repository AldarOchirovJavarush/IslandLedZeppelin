package com.javarush.island.ochirov.organism.plant;

import com.javarush.island.ochirov.configs.records.OrganismConfig;
import com.javarush.island.ochirov.organism.RegisteredOrganism;
import com.javarush.island.ochirov.services.AbstractOrganismService;

@RegisteredOrganism(configKey = "grass")
public class Grass extends Plant {
    public Grass(OrganismConfig config, AbstractOrganismService deathService, AbstractOrganismService reproduceService) {
        super(config, deathService, reproduceService);
    }
}
