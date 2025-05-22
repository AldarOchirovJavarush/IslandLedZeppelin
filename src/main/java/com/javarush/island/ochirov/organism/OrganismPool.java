package com.javarush.island.ochirov.organism;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrganismPool {
    private static final Queue<Organism> POOL = new ConcurrentLinkedQueue<>();

    public static Organism acquire(String key) {
        var org = POOL.poll();
        if (org != null) {
            org.init();
            return org;
        }

        return OrganismFactory.createOrganism(key);
    }

    public static void release(Organism organism) {
        POOL.offer(organism);
    }
}
