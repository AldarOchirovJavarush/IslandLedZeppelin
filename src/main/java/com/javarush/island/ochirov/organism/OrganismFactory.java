package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.organism.animal.carnivore.Wolf;
import com.javarush.island.ochirov.organism.animal.herbivore.Rabbit;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrganismFactory {
    private static final Map<String, Constructor<? extends Organism>> CONSTRUCTORS = new HashMap<>();
    private static final Map<String, OrganismConfig> CONFIGS = new HashMap<>();

    static {
        registerAnnotatedOrganisms();
    }

    private static void registerAnnotatedOrganisms() {
        registerOrganism(Wolf.class, new OrganismConfig("wolf", "W", 3));
        registerOrganism(Rabbit.class, new OrganismConfig("rabbit", "R", 3));
    }

    private static void registerOrganism(Class<? extends Organism> clazz, OrganismConfig config) {
        var annotation = clazz.getAnnotation(RegisteredOrganism.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Class " + clazz + " must have @RegisteredOrganism");
        }
        try {
            var constructor = clazz.getDeclaredConstructor(OrganismConfig.class);
            var configKey = annotation.configKey();

            CONSTRUCTORS.put(configKey, constructor);
            CONFIGS.put(configKey, config);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Missing OrganismConfig constructor in " + clazz.getSimpleName(), e);
        }
    }

    static Organism createOrganism(String type) {
        var config = CONFIGS.get(type);
        if (config == null) {
            throw new IllegalArgumentException("Unknown organism type: " + type);
        }
        try {
            var constructor = CONSTRUCTORS.get(type);
            return constructor.newInstance(config);
        } catch (Exception e) {
            throw new RuntimeException("Error creating " + type, e);
        }
    }

    public static Set<String> getAllDisplaySymbols() {
        return CONFIGS.values().stream()
                .map(OrganismConfig::getDisplaySymbol)
                .collect(Collectors.toSet());
    }
}
