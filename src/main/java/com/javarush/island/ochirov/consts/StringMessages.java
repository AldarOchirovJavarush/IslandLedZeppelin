package com.javarush.island.ochirov.consts;

public final class StringMessages implements StringConstants {
    private StringMessages() {
    }

    public final static String CURRENT_ISLAND_STATE = "\n=== CURRENT ISLAND STATE ===";
    public final static String MOVEMENT_MESSAGE = "%s%d moved from (%d,%d) to (%d,%d)";
    public final static String EATING_MESSAGE = "%s%d eaten %s%d in (%d,%d)";
    public final static String DEATH_MESSAGE = "%s%d died in (%d,%d)";
    public final static String REPRODUCE_MESSAGE = "%s%d born %s%d in (%d,%d)";
}
