package com.javarush.island.ochirov;

import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.island.Island;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.OrganismFactory;

import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ConsoleOutputManager {
    private static final Lock consoleLock = new ReentrantLock();

    public static void printWithLock(String message) {
        consoleLock.lock();
        try {
            System.out.println(message);
        } finally {
            consoleLock.unlock();
        }
    }

    public static void printIslandState(Island island) {
        consoleLock.lock();
        try {
            System.out.println("\n=== ТЕКУЩЕЕ СОСТОЯНИЕ ОСТРОВА ===");
            for (var y = 0; y < island.getHeight(); y++) {
                var row = new StringBuilder();
                for (var x = 0; x < island.getWidth(); x++) {
                    var cell = island.getCell(x, y).orElseThrow();
                    row.append(formatCell(cell));
                }
                System.out.println(row);
            }
        } finally {
            consoleLock.unlock();
        }
    }

    private static String formatCell(Cell cell) {
        var allSymbols = OrganismFactory.getAllDisplaySymbols();
        var organismsBySymbol = cell.getOrganisms().stream()
                .collect(Collectors.groupingBy(
                        organism -> organism.getConfig().getDisplaySymbol(),
                        Collectors.mapping(Organism::getId, Collectors.toList())
                ));
        return allSymbols.stream()
                .map(symbol -> {
                    var ids = organismsBySymbol.getOrDefault(symbol, Collections.emptyList());
                    return ids.isEmpty()
                            ? String.format("%s:0", symbol)
                            : String.format("%s:%d(%s)",
                            symbol,
                            ids.size(),
                            ids.stream().map(String::valueOf).collect(Collectors.joining(","))
                    );
                })
                .collect(Collectors.joining(" ", "[", "]"));
    }
}