package com.javarush.island.ochirov.simulation;

import com.javarush.island.ochirov.utils.view.ConsoleOutputManager;
import com.javarush.island.ochirov.consts.StringErrors;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.island.Island;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.behavior.Dying;
import com.javarush.island.ochirov.organism.behavior.Eater;
import com.javarush.island.ochirov.organism.behavior.Movable;
import com.javarush.island.ochirov.organism.behavior.Reproducible;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationStep {
    private final ExecutorService workers;
    private final List<LifeCyclePhase> phases = List.of(
            LifeCyclePhase.MOVEMENT,
            LifeCyclePhase.EATING,
            LifeCyclePhase.DEATH,
            LifeCyclePhase.REPRODUCE
    );

    public SimulationStep(int threads) {
        this.workers = Executors.newFixedThreadPool(threads);
    }

    public CompletableFuture<Void> processLifeCycle(Island island) {
        CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);

        for (var phase : phases) {
            chain = chain.thenCompose(v -> processPhase(island, phase));
        }

        return chain;
    }

    private CompletableFuture<Void> processPhase(Island island, LifeCyclePhase phase) {
        List<CompletableFuture<Void>> phaseActions = new ArrayList<>();

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                island.getCell(x, y).ifPresent(cell ->
                        phaseActions.addAll(processCell(cell, phase))
                );
            }
        }

        return CompletableFuture.allOf(phaseActions.toArray(new CompletableFuture[0]));
    }

    private List<CompletableFuture<Void>> processCell(Cell cell, LifeCyclePhase phase) {
        List<CompletableFuture<Void>> actions = new ArrayList<>();
        List<Organism> organisms = new ArrayList<>(cell.getOrganisms());

        for (var organism : organisms) {
            switch (phase) {
                case MOVEMENT:
                    if (organism instanceof Movable movable) {
                        actions.add(executeAction(movable::move));
                    }
                    break;

                case EATING:
                    if (organism instanceof Eater eater) {
                        actions.add(executeAction(eater::eat));
                    }
                    break;

                case DEATH:
                    if (organism instanceof Dying dying) {
                        actions.add(executeAction(dying::die));
                    }
                    break;

                case REPRODUCE:
                    if (organism instanceof Reproducible reproducible) {
                        actions.add(executeAction(reproducible::reproduce));
                    }
                    break;
            }
        }
        return actions;
    }

    private CompletableFuture<Void> executeAction(Runnable action) {
        return CompletableFuture.runAsync(() -> {
            try {
                action.run();
            } catch (Exception e) {
                ConsoleOutputManager.printWithLock(String.format(StringErrors.ACTION_FAILED, e.getMessage()));
            }
        }, workers);
    }

    public void shutdown() {
        workers.shutdown();
    }
}
