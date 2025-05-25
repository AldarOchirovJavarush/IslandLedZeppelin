package com.javarush.island.ochirov.utils.view;

import com.javarush.island.ochirov.consts.StringMessages;
import com.javarush.island.ochirov.island.Cell;
import com.javarush.island.ochirov.island.Island;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.utils.OrganismFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ConsoleOutputManager {
    private static final Lock LOCK = new ReentrantLock();
    private static final StringBuilder ISLAND_STATE_ROW = new StringBuilder(512);
    private static boolean detailedLog;
    private static int consoleViewIslandHeight;
    private static int consoleViewIslandWidth;

    private ConsoleOutputManager() {
    }

    public static void init(boolean detailedLog,
                            int consoleViewIslandHeight,
                            int consoleViewIslandWidth) {
        ConsoleOutputManager.detailedLog = detailedLog;
        ConsoleOutputManager.consoleViewIslandHeight = consoleViewIslandHeight;
        ConsoleOutputManager.consoleViewIslandWidth = consoleViewIslandWidth;
    }

    public static void printWithLock(String message) {
        LOCK.lock();
        try {
            System.out.println(message);
        } finally {
            LOCK.unlock();
        }
    }

    public static void printIslandState(Island island) {
        var height = Math.min(consoleViewIslandHeight, island.getHeight());
        var width = Math.min(consoleViewIslandWidth, island.getWidth());
        LOCK.lock();
        try {
            System.out.println(StringMessages.CURRENT_ISLAND_STATE);
            for (var y = 0; y < height; y++) {
                ISLAND_STATE_ROW.setLength(0);
                for (var x = 0; x < width; x++) {
                    var cell = island.getCell(x, y).orElseThrow();
                    ISLAND_STATE_ROW.append(formatCell(cell));
                }
                System.out.println(ISLAND_STATE_ROW);
            }
        } finally {
            LOCK.unlock();
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
                .map(symbol -> formatOrganismGroup(
                        symbol,
                        organismsBySymbol.getOrDefault(symbol, Collections.emptyList()),
                        detailedLog))
                .collect(Collectors.joining(" ", "[", "]"));
    }

    private static String formatOrganismGroup(String symbol, List<Integer> ids, boolean detailedLog) {
        if (detailedLog) {
            return ids.isEmpty()
                    ? String.format("%s:0", symbol)
                    : String.format("%s:%d(%s)",
                    symbol,
                    ids.size(),
                    ids.stream().map(String::valueOf).collect(Collectors.joining(","))
            );
        } else {
            return String.format("%s:%d", symbol, ids.size());
        }
    }
}