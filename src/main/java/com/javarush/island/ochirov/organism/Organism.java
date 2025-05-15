package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.island.Cell;
import lombok.Getter;
import lombok.Setter;

public abstract class Organism {
    @Getter
    protected static int COUNT = 0;
    @Getter
    protected final int id;
    @Getter
    protected final OrganismConfig config;
    @Getter
    @Setter
    protected Cell currentCell;

    public Organism(OrganismConfig config) {
        id = ++COUNT;
        this.config = config;
    }

    public void reset() {

    }
}
