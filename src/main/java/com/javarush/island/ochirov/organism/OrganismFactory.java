package com.javarush.island.ochirov.organism;

import com.javarush.island.ochirov.configs.OrganismConfig;
import com.javarush.island.ochirov.consts.StringErrors;
import com.javarush.island.ochirov.organism.animal.carnivore.Wolf;
import com.javarush.island.ochirov.organism.animal.herbivore.Rabbit;
import com.javarush.island.ochirov.services.*;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrganismFactory {
    private static final Map<String, Constructor<? extends Organism>> CONSTRUCTORS = new HashMap<>();
    private static Map<String, OrganismConfig> configs;
    private static AbstractOrganismService movementService;
    private static AbstractOrganismService eatingService;
    private static AbstractOrganismService deathService;
    private static AbstractOrganismService reproduceService;
    private static final Class<?>[] serviceConstructorParams = {
            OrganismConfig.class,
            AbstractOrganismService.class,
            AbstractOrganismService.class,
            AbstractOrganismService.class,
            AbstractOrganismService.class
    };

    private OrganismFactory() {
    }

    public static void init(
            Map<String, OrganismConfig> configs,
            AbstractOrganismService movementService,
            AbstractOrganismService eatingService,
            AbstractOrganismService deathService,
            AbstractOrganismService reproduceService) {
        OrganismFactory.configs = configs;
        OrganismFactory.movementService = movementService;
        OrganismFactory.eatingService = eatingService;
        OrganismFactory.deathService = deathService;
        OrganismFactory.reproduceService = reproduceService;

        registerAnnotatedOrganisms();
    }

    private static void registerAnnotatedOrganisms() {
        registerOrganism(Wolf.class);
        registerOrganism(Rabbit.class);
    }

    private static void registerOrganism(Class<? extends Organism> clazz) {
        var annotation = clazz.getAnnotation(RegisteredOrganism.class);
        if (annotation == null) {
            throw new IllegalArgumentException(String.format(StringErrors.REGISTERED_ORGANISM_REQUIRED_TEMPLATE, clazz));
        }

        var configKey = annotation.configKey();
        if (!configs.containsKey(configKey)) {
            throw new IllegalStateException(String.format(StringErrors.NO_CONFIG_FOUND, configKey));
        }

        try {
            var constructor = clazz.getDeclaredConstructor(serviceConstructorParams);
            CONSTRUCTORS.put(configKey, constructor);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format(StringErrors.MISSING_ORGANISM_CONFIG_CONSTRUCTOR, clazz.getSimpleName()));
        }
    }

    static Organism createOrganism(String type) {
        var config = configs.get(type);
        if (config == null) {
            throw new IllegalArgumentException(String.format(StringErrors.UNKNOWN_ORGANISM_TYPE, type));
        }
        try {
            var constructor = CONSTRUCTORS.get(type);
            return constructor.newInstance(
                    config,
                    movementService,
                    eatingService,
                    deathService,
                    reproduceService);
        } catch (Exception e) {
            throw new RuntimeException(String.format(StringErrors.ERROR_CREATING_ORGANISM, type));
        }
    }

    public static Set<String> getAllDisplaySymbols() {
        return configs.values().stream().map(OrganismConfig::displaySymbol).collect(Collectors.toSet());
    }
}
