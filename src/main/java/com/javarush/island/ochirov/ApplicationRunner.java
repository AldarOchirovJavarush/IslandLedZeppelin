package com.javarush.island.ochirov;

import com.javarush.island.ochirov.simulation.Simulation;

import java.util.concurrent.TimeUnit;

public class ApplicationRunner {
    private static final int SIMULATION_DURATION_SECONDS = 30;
    private static final int ISLAND_WIDTH = 2;
    private static final int ISLAND_HEIGHT = 2;

    public static void main(String[] args) throws InterruptedException {
        Simulation simulation = new Simulation(ISLAND_WIDTH, ISLAND_HEIGHT);
        simulation.start();

        Runtime.getRuntime().addShutdownHook(new Thread(simulation::stop));

        try {
            TimeUnit.SECONDS.sleep(SIMULATION_DURATION_SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            simulation.stop();
        }
    }
}
