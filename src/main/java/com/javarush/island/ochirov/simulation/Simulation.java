package com.javarush.island.ochirov.simulation;

import com.javarush.island.ochirov.ConsoleOutputManager;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.island.Island;
import com.javarush.island.ochirov.organism.OrganismPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {
    private final Island island;
    private final ScheduledExecutorService scheduler;
    private final SimulationStep simulationStep;
    private final OrganismPool pool;

    public Simulation(int width, int height) {
        this.island = new Island(width, height);
        this.pool = new OrganismPool();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.simulationStep = new SimulationStep(
                Math.max(2, Runtime.getRuntime().availableProcessors() - 1)
        );
    }

    public void start() {
        initializeIsland();
        scheduleSimulationSteps();
    }

    private void initializeIsland() {
        for (var x = 0; x < island.getWidth(); x++) {
            for (var y = 0; y < island.getHeight(); y++) {
                island.getCell(x, y).ifPresent(this::initializeCell);
            }
        }
    }

    private void initializeCell(Cell cell) {
        try {
            var wolf = pool.acquire("wolf");
            var rabbit = pool.acquire("rabbit");

            cell.addOrganism(wolf);
            cell.addOrganism(rabbit);

            wolf.setCurrentCell(cell);
            rabbit.setCurrentCell(cell);
        } catch (Exception e) {
            System.err.println("Cell initialization failed: " + e.getMessage());
        }
    }

    private void scheduleSimulationSteps() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                runSimulationCycle();
            } catch (Exception e) {
                System.err.println("Simulation cycle failed: " + e.getMessage());
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    private void runSimulationCycle() throws ExecutionException, InterruptedException {
        simulationStep.processLifeCycle(island).get();
        ConsoleOutputManager.printIslandState(island);
    }

    private int getTotalOrganisms(Island island) {
        var count = 0;
        for (var x = 0; x < island.getWidth(); x++) {
            for (var y = 0; y < island.getHeight(); y++) {
                count += island.getCell(x, y)
                        .map(cell -> cell.getOrganisms().size())
                        .orElse(0);
            }
        }
        return count;
    }

    public void stop() {
        try {
            scheduler.shutdown();
            simulationStep.shutdown();
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
