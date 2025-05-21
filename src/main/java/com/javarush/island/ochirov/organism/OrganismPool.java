package com.javarush.island.ochirov.organism;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrganismPool {
    private static final Queue<Organism> POOL = new ConcurrentLinkedQueue<>();

    public static Organism acquire(String key) {
        var org = POOL.poll();
        return org != null ? org : OrganismFactory.createOrganism(key);
    }

    public static void release(Organism organism) {
        organism.reset();
        POOL.offer(organism);
    }
}
