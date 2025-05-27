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
        var config = ConfigsLoader.loadConfig();
        init(config);

        var simulationConfig = config.simulation();
        var simulation = new Simulation(simulationConfig);
        simulation.start();

        Runtime.getRuntime().addShutdownHook(new Thread(simulation::stop));

        try {
            TimeUnit.SECONDS.sleep(simulationConfig.durationSeconds());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            simulation.stop();
        }
    }

    private static void init(AppConfig appConfig) {
        var simulationConfig = appConfig.simulation();
        var detailedLog = simulationConfig.detailedLog();

        ConsoleOutputManager.init(detailedLog,
                simulationConfig.consoleViewIslandHeight(),
                simulationConfig.consoleViewIslandWidth());

        AbstractOrganismService movementService = new MovementService(detailedLog);
        AbstractOrganismService eatingService = new EatingService(detailedLog);
        AbstractOrganismService deathService = new DeathService(detailedLog);
        AbstractOrganismService reproduceService = new ReproduceService(detailedLog);

        OrganismFactory.init(
                appConfig.organisms(),
                movementService,
                eatingService,
                deathService,
                reproduceService);
    }
}
