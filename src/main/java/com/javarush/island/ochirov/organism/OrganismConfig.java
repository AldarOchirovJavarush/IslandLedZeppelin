package com.javarush.island.ochirov.organism;

import java.util.Map;

public record OrganismConfig(
        String key,
        String displaySymbol,
        int maxPerCell,
        int speed,
        double satiety,
        double weight,
        double starvation,
        Map<String, Integer> eatProbabilities) {
}
