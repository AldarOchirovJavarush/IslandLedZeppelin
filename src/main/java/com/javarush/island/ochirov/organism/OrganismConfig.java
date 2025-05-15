package com.javarush.island.ochirov.organism;

public class OrganismConfig {
    private final String key;
    private final String displaySymbol;
    private final int maxPerCell;

    public OrganismConfig(String key, String displaySymbol, int maxPerCell) {
        this.key = key;
        this.displaySymbol = displaySymbol;
        this.maxPerCell = maxPerCell;
    }

    public String getKey() {
        return key;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public int getMaxPerCell() {
        return maxPerCell;
    }
}
