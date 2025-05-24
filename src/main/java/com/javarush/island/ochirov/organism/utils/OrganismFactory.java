package com.javarush.island.ochirov.organism.utils;

import com.javarush.island.ochirov.configs.records.OrganismConfig;
import com.javarush.island.ochirov.consts.StringErrors;
import com.javarush.island.ochirov.organism.Organism;
import com.javarush.island.ochirov.organism.RegisteredOrganism;
import com.javarush.island.ochirov.organism.animal.carnivore.Wolf;
import com.javarush.island.ochirov.organism.animal.herbivore.Rabbit;
import com.javarush.island.ochirov.organism.plant.Grass;
import com.javarush.island.ochirov.organism.plant.Plant;
import com.javarush.island.ochirov.services.*;
import com.javarush.island.ochirov.utils.Randomizer;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrganismFactory {
    private static final Map<String, Constructor<? extends Organism>> CONSTRUCTORS = new HashMap<>();
    private static Map<String, OrganismConfig> configs;
    private static AbstractOrganismService movementService;
    private static AbstractOrganismService eatingService;
    private static AbstractOrganismService deathService;
    private static AbstractOrganismService reproduceService;
    private static final Class<?>[] animalServiceConstructorParams = {
            OrganismConfig.class,
            AbstractOrganismService.class,
            AbstractOrganismService.class,
            AbstractOrganismService.class,
            AbstractOrganismService.class
    };
    private static final Class<?>[] plantServiceConstructorParams = {
            OrganismConfig.class,
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
        registerOrganism(Grass.class);
    }

    private static void registerOrganism(Class<? extends Organism> clazz) {
        var annotation = clazz.getAnnotation(RegisteredOrganism.class);
        if (annotation == null) {
            throw new IllegalArgumentException(String.format(
                    StringErrors.REGISTERED_ORGANISM_REQUIRED_TEMPLATE, clazz));
        }

        var configKey = annotation.configKey();
        if (!configs.containsKey(configKey)) {
            throw new IllegalStateException(String.format(
                    StringErrors.NO_CONFIG_FOUND, configKey));
        }

        try {
            var serviceParams = isPlant(clazz)
                    ? plantServiceConstructorParams : animalServiceConstructorParams;
            var constructor = clazz.getDeclaredConstructor(serviceParams);
            CONSTRUCTORS.put(configKey, constructor);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format(StringErrors.MISSING_ORGANISM_CONFIG_CONSTRUCTOR, clazz.getSimpleName()));
        }
    }

    static Organism createOrganism(String type) {
        return create(type);
    }

    static List<Organism> createRandomOrganisms(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException(StringErrors.ILLEGAL_PERCENT);
        }

        return configs.entrySet().stream()
                .flatMap(entry -> {
                    var type = entry.getKey();
                    var maxCount = entry.getValue().maxPerCell();
                    var count = Randomizer.getRandom(0, Math.max(1, (percent * maxCount) / 100) + 1);

                    return Stream.generate(() -> create(type))
                            .limit(count);
                })
                .collect(Collectors.toList());
    }

    private static Organism create(String type) {
        var config = configs.get(type);
        if (config == null) {
            throw new IllegalArgumentException(String.format(StringErrors.UNKNOWN_ORGANISM_TYPE, type));
        }
        try {
            var constructor = CONSTRUCTORS.get(type);
            return isPlant(constructor.getDeclaringClass())
                    ? constructor.newInstance(
                    config,
                    deathService,
                    reproduceService)
                    : constructor.newInstance(
                    config,
                    movementService,
                    eatingService,
                    deathService,
                    reproduceService);
        } catch (Exception e) {
            throw new RuntimeException(String.format(StringErrors.ERROR_CREATING_ORGANISM, type));
        }
    }

    private static boolean isPlant(Class<? extends Organism> clazz) {
        return Plant.class.isAssignableFrom(clazz);
    }

    public static Set<String> getAllDisplaySymbols() {
        return configs.values().stream().map(OrganismConfig::displaySymbol).collect(Collectors.toSet());
    }
}
