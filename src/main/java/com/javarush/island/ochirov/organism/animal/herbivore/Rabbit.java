package com.javarush.island.ochirov.organism.animal.herbivore;

import com.javarush.island.ochirov.organism.OrganismConfig;
import com.javarush.island.ochirov.organism.RegisteredOrganism;

@RegisteredOrganism(configKey = "rabbit")
public class Rabbit extends Herbivore {
    public Rabbit(OrganismConfig config) {
        super(config);
    }
}
