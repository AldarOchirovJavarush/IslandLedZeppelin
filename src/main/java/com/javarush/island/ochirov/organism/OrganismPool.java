package com.javarush.island.ochirov.organism;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrganismPool {
    private final Queue<Organism> pool = new ConcurrentLinkedQueue<>();

    public Organism acquire(String key) {
        var org = pool.poll();
        return org != null ? org : OrganismFactory.createOrganism(key);
    }

    public void release(Organism organism) {
        organism.reset();
        pool.offer(organism);
    }
}
