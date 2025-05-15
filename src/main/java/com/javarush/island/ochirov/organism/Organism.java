package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.island.Cell;

public abstract class Organism {
    protected static int COUNT = 0;
    protected final int id;
    protected final OrganismConfig config;
    protected Cell currentCell;

    public Organism(OrganismConfig config) {
        id = ++COUNT;
        this.config = config;
    }

    public int getId() {
        return id;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public OrganismConfig getConfig() {
        return config;
    }

    public void reset() {

    }
}
