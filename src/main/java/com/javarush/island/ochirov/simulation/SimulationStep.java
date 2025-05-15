package com.javarush.island.ochirov.simulation;

import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.island.Island;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.behavior.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationStep {
    private final ExecutorService workers;

    public SimulationStep(int threads) {
        this.workers = Executors.newFixedThreadPool(threads);
    }

    public CompletableFuture<Void> processLifeCycle(Island island) {
        List<CompletableFuture<Void>> allActions = new ArrayList<>();
        for (var x = 0; x < island.getWidth(); x++) {
            for (var y = 0; y < island.getHeight(); y++) {
                island.getCell(x, y).ifPresent(cell -> allActions.addAll(processCell(cell, island)));
            }
        }

        return CompletableFuture.allOf(allActions.toArray(new CompletableFuture[0]));
    }

    private List<CompletableFuture<Void>> processCell(Cell cell, Island island) {
        List<CompletableFuture<Void>> cellActions = new ArrayList<>();
        List<Organism> organisms = new ArrayList<>(cell.getOrganisms());

        for (var organism : organisms) {
            if (organism instanceof Movable) {
                cellActions.add(executeAction(() ->
                        ((Movable) organism).move(island)));
            }
        }

        return cellActions;
    }

    private CompletableFuture<Void> executeAction(Runnable action) {
        return CompletableFuture.runAsync(() -> {
            try {
                action.run();
            } catch (Exception e) {
                System.err.println("Action failed: " + e.getMessage());
            }
        }, workers);
    }

    public void shutdown() {
        workers.shutdown();
    }
}
