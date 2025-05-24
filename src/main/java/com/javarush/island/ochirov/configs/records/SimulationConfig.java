package com.javarush.island.ochirov.configs.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.concurrent.TimeUnit;

public record SimulationConfig(
        @JsonProperty("durationSeconds") int durationSeconds,
        @JsonProperty("width") int width,
        @JsonProperty("height") int height,
        @JsonProperty("initDelay") long initDelay,
        @JsonProperty("period") long period,
        @JsonProperty("timeUnit") TimeUnit timeUnit,
        @JsonProperty("startPercentOrganismsCount") int startPercentOrganismsCount,
        @JsonProperty("detailedLog") boolean detailedLog) {
}
