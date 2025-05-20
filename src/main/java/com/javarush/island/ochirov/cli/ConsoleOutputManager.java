package com.javarush.island.ochirov.cli;

import com.javarush.island.ochirov.consts.StringMessages;
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
    private static final StringBuilder islandStateRow = new StringBuilder(512);

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
            System.out.println(StringMessages.CURRENT_ISLAND_STATE);
            for (var y = 0; y < island.getHeight(); y++) {
                islandStateRow.setLength(0);
                for (var x = 0; x < island.getWidth(); x++) {
                    var cell = island.getCell(x, y).orElseThrow();
                    islandStateRow.append(formatCell(cell));
                }
                System.out.println(islandStateRow);
            }
        } finally {
            consoleLock.unlock();
        }
    }

    private static String formatCell(Cell cell) {
        var allSymbols = OrganismFactory.getAllDisplaySymbols();
        var organismsBySymbol = cell.getOrganisms().stream()
                .collect(Collectors.groupingBy(
                        organism -> organism.getConfig().displaySymbol(),
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