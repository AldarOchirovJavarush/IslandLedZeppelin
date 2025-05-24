package com.javarush.island.ochirov.configs;

import com.javarush.island.ochirov.organism.OrganismConfig;

import java.util.HashMap;
import java.util.Map;

public class DefaultOrganismConfigFactory {
    private static final Map<String, OrganismConfig> DEFAULT_CONFIGS = Map.of(
            "wolf", new OrganismConfig(
                    "wolf",
                    "W",
                    5,
                    3,
                    8.0,
                    50.0,
                    1.0,
                    50,
                    Map.of("rabbit", 60)),

            "rabbit", new OrganismConfig(
                    "rabbit",
                    "R",
                    10,
                    2,
                    0.45,
                    2.0,
                    0.2,
                    70,
                    Map.of("wolf", 0))
    );

    public static Map<String, OrganismConfig> createDefaultConfigs() {
        return new HashMap<>(DEFAULT_CONFIGS);
    }
}
