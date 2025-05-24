package com.javarush.island.ochirov.configs.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record AppConfig(
        @JsonProperty("simulation") SimulationConfig simulation,
        @JsonProperty("organisms") Map<String, OrganismConfig> organisms) {
}
