package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.configs.OrganismConfig;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.organism.behavior.Dying;
import com.javarush.island.ochirov.organism.behavior.Reproducible;
import com.javarush.island.ochirov.services.AbstractOrganismService;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Organism implements Dying, Reproducible {
    @Getter
    protected static int COUNT = 0;
    @Getter
    protected int id;
    @Getter
    protected final OrganismConfig config;
    @Getter
    @Setter
    protected Cell currentCell;
    protected final Lock lock = new ReentrantLock(true);
    private final AbstractOrganismService deathService;
    private final AbstractOrganismService reproduceService;
    @Getter
    @Setter
    protected boolean isAlive;

    public Organism(OrganismConfig config, AbstractOrganismService deathService, AbstractOrganismService reproduceService) {
        this.config = config;
        this.deathService = deathService;
        this.reproduceService = reproduceService;
        init();
    }

    protected void init() {
        id = ++COUNT;
        isAlive = true;
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    protected void action(AbstractOrganismService service) {
        service.action(this);
    }

    @Override
    public void die() {
        action(deathService);
    }

    @Override
    public void reproduce() {
        action(reproduceService);
    }
}
