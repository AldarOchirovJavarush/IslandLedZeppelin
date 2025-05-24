package com.javarush.island.ochirov;

import com.javarush.island.ochirov.configs.ConfigsLoader;
import com.javarush.island.ochirov.organism.OrganismFactory;
import com.javarush.island.ochirov.simulation.Simulation;

import java.util.concurrent.TimeUnit;

public class ApplicationRunner {
    public static void main(String[] args) {
        var config = ConfigsLoader.loadConfig();
        OrganismFactory.init(config.organisms());
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
