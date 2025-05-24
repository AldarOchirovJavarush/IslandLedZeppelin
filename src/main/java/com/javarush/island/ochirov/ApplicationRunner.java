package com.javarush.island.ochirov;

import com.javarush.island.ochirov.utils.view.ConsoleOutputManager;
import com.javarush.island.ochirov.configs.utils.ConfigsLoader;
import com.javarush.island.ochirov.organism.utils.OrganismFactory;
import com.javarush.island.ochirov.services.*;
import com.javarush.island.ochirov.simulation.Simulation;

import java.util.concurrent.TimeUnit;

public class ApplicationRunner {

    public static void main(String[] args) {
        var config = ConfigsLoader.loadConfig();
        ConsoleOutputManager.init(config.simulation().detailedLog());
        AbstractOrganismService movementService = new MovementService(config.simulation().detailedLog());
        AbstractOrganismService eatingService = new EatingService(config.simulation().detailedLog());
        AbstractOrganismService deathService = new DeathService(config.simulation().detailedLog());
        AbstractOrganismService reproduceService = new ReproduceService(config.simulation().detailedLog());
        OrganismFactory.init(
                config.organisms(),
                movementService,
                eatingService,
                deathService,
                reproduceService);
        var simulation = new Simulation(config.simulation());
        simulation.start();

        Runtime.getRuntime().addShutdownHook(new Thread(simulation::stop));

        try {
            TimeUnit.SECONDS.sleep(config.simulation().durationSeconds());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            simulation.stop();
        }
    }
}
