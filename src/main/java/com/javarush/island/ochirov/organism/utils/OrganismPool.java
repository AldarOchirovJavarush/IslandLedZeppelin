package com.javarush.island.ochirov.organism.utils;

import com.javarush.island.ochirov.organism.Organism;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrganismPool {
    private static final Map<String, Queue<Organism>> POOLS = new ConcurrentHashMap<>();

    public static Organism acquire(String key) {
        var organism = POOLS.getOrDefault(key, new ConcurrentLinkedQueue<>()).poll();
        if (organism != null) {
            organism.init();
            return organism;
        }

        return OrganismFactory.createOrganism(key);
    }

    public static List<Organism> acquire(int percent) {
        return OrganismFactory.createRandomOrganisms(percent);
    }

    public static void release(Organism organism) {
        POOLS.computeIfAbsent(organism.getConfig().key(), k -> new ConcurrentLinkedQueue<>())
                .add(organism);
    }
}
