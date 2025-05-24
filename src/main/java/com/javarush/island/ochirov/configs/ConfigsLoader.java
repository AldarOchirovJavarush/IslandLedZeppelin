package com.javarush.island.ochirov.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.island.ochirov.cli.ConsoleOutputManager;
import com.javarush.island.ochirov.consts.StringErrors;
import java.util.concurrent.TimeUnit;

public class ConfigsLoader {
    private static final String CONFIG_FILE = "ochirov/setting.yaml";
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    private ConfigsLoader() {
    }

    public static AppConfig loadConfig() {
        try (var input = ConfigsLoader.class
                .getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                return createDefaultConfigs();
            }

            return YAML_MAPPER.readValue(input, AppConfig.class);
        } catch (Exception e) {
            ConsoleOutputManager.printWithLock(String.format(StringErrors.ERROR_YAML_LOADING, e.getMessage()));
            return createDefaultConfigs();
        }
    }

    private static AppConfig createDefaultConfigs() {
        return new AppConfig(
                createDefaultSimulationConfig(),
                DefaultOrganismConfigFactory.createDefaultConfigs()
        );
    }

    private static SimulationConfig createDefaultSimulationConfig() {
        return new SimulationConfig(
                600,
                20,
                15,
                0L,
                2000L,
                TimeUnit.MILLISECONDS
        );
    }
}
