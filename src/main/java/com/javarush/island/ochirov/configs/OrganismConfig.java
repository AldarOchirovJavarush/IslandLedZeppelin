package com.javarush.island.ochirov.configs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record OrganismConfig(
        @JsonProperty("key") String key,
        @JsonProperty("displaySymbol") String displaySymbol,
        @JsonProperty("maxPerCell") int maxPerCell,
        @JsonProperty("speed") int speed,
        @JsonProperty("satiety") double satiety,
        @JsonProperty("weight") double weight,
        @JsonProperty("starvation") double starvation,
        @JsonProperty("reproduceProbability") int reproduceProbability,
        @JsonProperty("eatProbabilities") Map<String, Integer> eatProbabilities) {
}
