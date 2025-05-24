package com.javarush.island.ochirov.consts;

public final class StringErrors implements StringConstants {
    private StringErrors() {
    }

    public final static String REGISTERED_ORGANISM_REQUIRED_TEMPLATE = "Class %s must have @RegisteredOrganism";
    public final static String MISSING_ORGANISM_CONFIG_CONSTRUCTOR = "Missing OrganismConfig constructor in %s";
    public final static String UNKNOWN_ORGANISM_TYPE = "Unknown organism type: %s";
    public final static String ERROR_CREATING_ORGANISM = "Error creating %s";
    public final static String ILLEGAL_PERCENT = "Percent must be between 0 and 100";

    public final static String SIMULATION_CYCLE_FAILED = "Simulation cycle failed: %s";
    public final static String CURRENT_THREAD_INTERRUPTED = "Current thread interrupted: %s";

    public final static String ACTION_FAILED = "Action failed: %s";

    public final static String ERROR_YAML_LOADING = "Error loading YAML config: %s";
    public final static String NO_CONFIG_FOUND = "No config found for: %s";
}
