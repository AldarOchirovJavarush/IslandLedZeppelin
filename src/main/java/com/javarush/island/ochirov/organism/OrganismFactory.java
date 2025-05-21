package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.consts.StringErrors;
import com.javarush.island.ochirov.organism.animal.carnivore.Wolf;
import com.javarush.island.ochirov.organism.animal.herbivore.Rabbit;
import com.javarush.island.ochirov.services.AbstractOrganismService;
import com.javarush.island.ochirov.services.EatingService;
import com.javarush.island.ochirov.services.MovementService;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrganismFactory {
    private static final Map<String, Constructor<? extends Organism>> CONSTRUCTORS = new HashMap<>();
    private static final Map<String, OrganismConfig> CONFIGS = new HashMap<>();
    private static final AbstractOrganismService movementService = new MovementService();
    private static final AbstractOrganismService eatingService = new EatingService();

    static {
        registerAnnotatedOrganisms();
    }

    private static void registerAnnotatedOrganisms() {
        var probabilities = new HashMap<String, Integer>();
        probabilities.put("rabbit", 60);
        registerOrganism(Wolf.class, new OrganismConfig("wolf", "W", 3, 3, 8, 50, 1, probabilities));
        var probabilities2 = new HashMap<String, Integer>();
        probabilities2.put("wolf", 0);
        registerOrganism(Rabbit.class, new OrganismConfig("rabbit", "R", 3, 3, 0.45, 2, 0.2, probabilities2));
    }

    private static void registerOrganism(Class<? extends Organism> clazz, OrganismConfig config) {
        var annotation = clazz.getAnnotation(RegisteredOrganism.class);
        if (annotation == null) {
            throw new IllegalArgumentException(String.format(StringErrors.REGISTERED_ORGANISM_REQUIRED_TEMPLATE, clazz));
        }
        try {
            var constructor = clazz.getDeclaredConstructor(
                    OrganismConfig.class,
                    AbstractOrganismService.class,
                    AbstractOrganismService.class
            );
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
            return constructor.newInstance(config, movementService, eatingService);
        } catch (Exception e) {
            throw new RuntimeException(String.format(StringErrors.ERROR_CREATING_ORGANISM, type));
        }
    }

    public static Set<String> getAllDisplaySymbols() {
        return CONFIGS.values().stream().map(OrganismConfig::displaySymbol).collect(Collectors.toSet());
    }
}
