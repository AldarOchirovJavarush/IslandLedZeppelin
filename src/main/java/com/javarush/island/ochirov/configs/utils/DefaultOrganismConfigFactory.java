package com.javarush.island.ochirov.configs.utils;

import com.javarush.island.ochirov.configs.records.OrganismConfig;

import java.util.HashMap;
import java.util.Map;

public class DefaultOrganismConfigFactory {
    private DefaultOrganismConfigFactory () {
    }

    public static Map<String, OrganismConfig> createDefaultConfigs() {
        return new HashMap<>(DEFAULT_CONFIGS);
    }

    private static final Map<String, OrganismConfig> DEFAULT_CONFIGS = Map.of(
            "wolf", new OrganismConfig(
                    "wolf",
                    "W",
                    30,
                    3,
                    8.0,
                    50.0,
                    1.0,
                    30,
                    Map.of("rabbit", 60)),

            "rabbit", new OrganismConfig(
                    "rabbit",
                    "R",
                    10,
                    2,
                    0.45,
                    2.0,
                    0.2,
                    100,
                    Map.of("wolf", 0)),

            "grass", new OrganismConfig(
                    "grass",
                    "G",
                    200,
                    0,
                    0,
                    1.0,
                    0,
                    100,
                    Map.of("wolf", 0))
    );
}
