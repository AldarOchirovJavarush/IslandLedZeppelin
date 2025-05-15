package com.javarush.island.ochirov.organism.animal.carnivore;

import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.RegisteredOrganism;

@RegisteredOrganism(configKey = "wolf")
public class Wolf extends Carnivore {
    public Wolf(OrganismConfig config) {
        super(config);
    }
}
