package com.javarush.island.ochirov;

import com.javarush.island.ochirov.configs.records.AppConfig;
import com.javarush.island.ochirov.utils.view.ConsoleOutputManager;
import com.javarush.island.ochirov.configs.utils.ConfigsLoader;
import com.javarush.island.ochirov.organism.utils.OrganismFactory;
import com.javarush.island.ochirov.services.*;
import com.javarush.island.ochirov.simulation.Simulation;

import java.util.concurrent.TimeUnit;

public class ApplicationRunner {

    public static void main(String[] args) {
        var config = initEntities();

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

    private static AppConfig initEntities() {
        var config = ConfigsLoader.loadConfig();
        var simulationConfig = config.simulation();
        ConsoleOutputManager.init(simulationConfig.detailedLog(),
                simulationConfig.consoleViewIslandHeight(),
                simulationConfig.consoleViewIslandWidth());
        AbstractOrganismService movementService = new MovementService(simulationConfig.detailedLog());
        AbstractOrganismService eatingService = new EatingService(simulationConfig.detailedLog());
        AbstractOrganismService deathService = new DeathService(simulationConfig.detailedLog());
        AbstractOrganismService reproduceService = new ReproduceService(simulationConfig.detailedLog());

        OrganismFactory.init(
                config.organisms(),
                movementService,
                eatingService,
                deathService,
                reproduceService);

        return config;
    }
}
