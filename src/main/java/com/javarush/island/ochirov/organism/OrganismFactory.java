package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.consts.StringErrors;
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
            throw new IllegalArgumentException(String.format(StringErrors.REGISTERED_ORGANISM_REQUIRED_TEMPLATE, clazz));
        }
        try {
            var constructor = clazz.getDeclaredConstructor(OrganismConfig.class);
            var configKey = annotation.configKey();

            CONSTRUCTORS.put(configKey, constructor);
            CONFIGS.put(configKey, config);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format(StringErrors.MISSING_ORGANISM_CONFIG_CONSTRUCTOR, clazz.getSimpleName()));
        }
    }

    static Organism createOrganism(String type) {
        var config = CONFIGS.get(type);
        if (config == null) {
            throw new IllegalArgumentException(String.format(StringErrors.UNKNOWN_ORGANISM_TYPE, type));
        }
        try {
            var constructor = CONSTRUCTORS.get(type);
            return constructor.newInstance(config);
        } catch (Exception e) {
            throw new RuntimeException(String.format(StringErrors.ERROR_CREATING_ORGANISM, type));
        }
    }

    public static Set<String> getAllDisplaySymbols() {
        return CONFIGS.values().stream().map(OrganismConfig::displaySymbol).collect(Collectors.toSet());
    }
}
