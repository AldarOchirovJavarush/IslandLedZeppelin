package com.javarush.island.ochirov.configs.utils;

import com.javarush.island.ochirov.configs.records.OrganismConfig;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultOrganismConfigFactory {
    private DefaultOrganismConfigFactory() {
    }

    public static Map<String, OrganismConfig> createDefaultConfigs() {
        return new HashMap<>(DEFAULT_CONFIGS);
    }

    @Getter
    public enum OrganismType {
        WOLF("wolf", "Wf"),
        BOA("boa", "Bo"),
        FOX("fox", "F"),
        BEAR("bear", "Be"),
        EAGLE("eagle", "E"),
        HORSE("horse", "H"),
        DEER("deer", "Dr"),
        RABBIT("rabbit", "R"),
        MOUSE("mouse", "M"),
        GOAT("goat", "Gt"),
        SHEEP("sheep", "Sh"),
        BOAR("boar", "Bo"),
        BUFFALO("buffalo", "Bf"),
        DUCK("duck", "Dk"),
        CATERPILLAR("caterpillar", "C"),
        GRASS("grass", "Gs");

        private final String key;
        private final String displaySymbol;

        OrganismType(String key, String displaySymbol) {
            this.key = key;
            this.displaySymbol = displaySymbol;
        }
    }

    private static final Map.Entry<String, OrganismConfig> WOLF = Map.entry(
            OrganismType.WOLF.getKey(), new OrganismConfig(
                    OrganismType.WOLF.getKey(),
                    OrganismType.WOLF.getDisplaySymbol(),
                    30,
                    3,
                    8.0,
                    50.0,
                    1.0,
                    30,
                    Map.of(OrganismType.HORSE.getKey(), 10,
                            OrganismType.DEER.getKey(), 15,
                            OrganismType.RABBIT.getKey(), 60,
                            OrganismType.MOUSE.getKey(), 80,
                            OrganismType.GOAT.getKey(), 60,
                            OrganismType.SHEEP.getKey(), 70,
                            OrganismType.BOAR.getKey(), 15,
                            OrganismType.BUFFALO.getKey(), 10,
                            OrganismType.DUCK.getKey(), 10)));

    private static final Map.Entry<String, OrganismConfig> BOA = Map.entry(
            OrganismType.BOA.getKey(), new OrganismConfig(
                    OrganismType.BOA.getKey(),
                    OrganismType.BOA.getDisplaySymbol(),
                    30,
                    1,
                    3.0,
                    15.0,
                    1.5,
                    30,
                    Map.of(OrganismType.FOX.getKey(), 15,
                            OrganismType.RABBIT.getKey(), 20,
                            OrganismType.MOUSE.getKey(), 40,
                            OrganismType.DUCK.getKey(), 10)));

    private static final Map.Entry<String, OrganismConfig> FOX = Map.entry(
            OrganismType.FOX.getKey(), new OrganismConfig(
                    OrganismType.FOX.getKey(),
                    OrganismType.FOX.getDisplaySymbol(),
                    30,
                    2,
                    2.0,
                    8.0,
                    0.5,
                    30,
                    Map.of(OrganismType.RABBIT.getKey(), 70,
                            OrganismType.MOUSE.getKey(), 90,
                            OrganismType.DUCK.getKey(), 60,
                            OrganismType.CATERPILLAR.getKey(), 40)));

    private static final Map.Entry<String, OrganismConfig> BEAR = Map.entry(
            OrganismType.BEAR.getKey(), new OrganismConfig(
                    OrganismType.BEAR.getKey(),
                    OrganismType.BEAR.getDisplaySymbol(),
                    5,
                    2,
                    80.0,
                    500.0,
                    10.0,
                    15,
                    Map.of(OrganismType.BOA.getKey(), 80,
                            OrganismType.HORSE.getKey(), 40,
                            OrganismType.DEER.getKey(), 80,
                            OrganismType.RABBIT.getKey(), 80,
                            OrganismType.MOUSE.getKey(), 90,
                            OrganismType.GOAT.getKey(), 70,
                            OrganismType.SHEEP.getKey(), 70,
                            OrganismType.BOAR.getKey(), 50,
                            OrganismType.BUFFALO.getKey(), 20,
                            OrganismType.DUCK.getKey(), 10)));

    private static final Map.Entry<String, OrganismConfig> EAGLE = Map.entry(
            OrganismType.EAGLE.getKey(), new OrganismConfig(
                    OrganismType.EAGLE.getKey(),
                    OrganismType.EAGLE.getDisplaySymbol(),
                    20,
                    3,
                    1.0,
                    6.0,
                    0.2,
                    30,
                    Map.of(OrganismType.FOX.getKey(), 10,
                            OrganismType.RABBIT.getKey(), 80,
                            OrganismType.MOUSE.getKey(), 90,
                            OrganismType.DUCK.getKey(), 10)));

    private static final Map.Entry<String, OrganismConfig> HORSE = Map.entry(
            OrganismType.HORSE.getKey(), new OrganismConfig(
                    OrganismType.HORSE.getKey(),
                    OrganismType.HORSE.getDisplaySymbol(),
                    20,
                    4,
                    60.0,
                    400.0,
                    10.0,
                    20,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> DEER = Map.entry(
            OrganismType.DEER.getKey(), new OrganismConfig(
                    OrganismType.DEER.getKey(),
                    OrganismType.DEER.getDisplaySymbol(),
                    20,
                    4,
                    50.0,
                    300.0,
                    5.0,
                    20,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> RABBIT = Map.entry(
            OrganismType.RABBIT.getKey(), new OrganismConfig(
                    OrganismType.RABBIT.getKey(),
                    OrganismType.RABBIT.getDisplaySymbol(),
                    10,
                    2,
                    0.45,
                    2.0,
                    0.2,
                    30,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> MOUSE = Map.entry(
            OrganismType.MOUSE.getKey(), new OrganismConfig(
                    OrganismType.MOUSE.getKey(),
                    OrganismType.MOUSE.getDisplaySymbol(),
                    500,
                    1,
                    0.01,
                    0.05,
                    0.001,
                    90,
                    Map.of(OrganismType.CATERPILLAR.getKey(), 90,
                            OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> GOAT = Map.entry(
            OrganismType.GOAT.getKey(), new OrganismConfig(
                    OrganismType.GOAT.getKey(),
                    OrganismType.GOAT.getDisplaySymbol(),
                    140,
                    3,
                    10.0,
                    60.0,
                    1.0,
                    30,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> SHEEP = Map.entry(
            OrganismType.SHEEP.getKey(), new OrganismConfig(
                    OrganismType.SHEEP.getKey(),
                    OrganismType.SHEEP.getDisplaySymbol(),
                    140,
                    3,
                    10.0,
                    60.0,
                    1.0,
                    30,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> BOAR = Map.entry(
            OrganismType.BOAR.getKey(), new OrganismConfig(
                    OrganismType.BOAR.getKey(),
                    OrganismType.BOAR.getDisplaySymbol(),
                    50,
                    2,
                    50.0,
                    400.0,
                    5.0,
                    30,
                    Map.of(OrganismType.MOUSE.getKey(), 50,
                            OrganismType.CATERPILLAR.getKey(), 90,
                            OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> BUFFALO = Map.entry(
            OrganismType.BUFFALO.getKey(), new OrganismConfig(
                    OrganismType.BUFFALO.getKey(),
                    OrganismType.BUFFALO.getDisplaySymbol(),
                    10,
                    3,
                    100.0,
                    700.0,
                    20.0,
                    30,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> DUCK = Map.entry(
            OrganismType.DUCK.getKey(), new OrganismConfig(
                    OrganismType.DUCK.getKey(),
                    OrganismType.DUCK.getDisplaySymbol(),
                    200,
                    4,
                    0.15,
                    1.0,
                    0.02,
                    20,
                    Map.of(OrganismType.CATERPILLAR.getKey(), 90,
                            OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> CATERPILLAR = Map.entry(
            OrganismType.CATERPILLAR.getKey(), new OrganismConfig(
                    OrganismType.CATERPILLAR.getKey(),
                    OrganismType.CATERPILLAR.getDisplaySymbol(),
                    1000,
                    0,
                    0.0,
                    0.01,
                    0.0,
                    20,
                    Map.of(OrganismType.GRASS.getKey(), 100)));

    private static final Map.Entry<String, OrganismConfig> GRASS = Map.entry(
            OrganismType.GRASS.getKey(), new OrganismConfig(
                    OrganismType.GRASS.getKey(),
                    OrganismType.GRASS.getDisplaySymbol(),
                    200,
                    0,
                    0,
                    1.0,
                    0,
                    100,
                    Collections.emptyMap()));

    private static final Map<String, OrganismConfig> DEFAULT_CONFIGS = Map.ofEntries(
            WOLF,
            BOA,
            FOX,
            BEAR,
            EAGLE,
            HORSE,
            DEER,
            RABBIT,
            MOUSE,
            GOAT,
            SHEEP,
            BOAR,
            BUFFALO,
            DUCK,
            CATERPILLAR,
            GRASS
    );
}
