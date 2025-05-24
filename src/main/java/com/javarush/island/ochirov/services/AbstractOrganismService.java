package com.javarush.island.ochirov.services;

import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.organism.Organism;

public abstract class AbstractOrganismService {
    private final boolean shouldLog;
    public abstract void action(Organism organism);

    protected AbstractOrganismService(boolean shouldLog) {
        this.shouldLog = shouldLog;
    }

    protected void log(String message) {
        if (shouldLog) {
            ConsoleOutputManager.printWithLock(message);
        }
    }
}
