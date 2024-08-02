package com.github.maximiliantyan.core;

import net.minecraft.network.chat.Component;

public enum PyroTemperatures {
    WARM(0, "temperature.warm"),
    HOT(1, "temperature.hot"),
    BURNING(2, "temperature.burning"),
    SCORCHING(3, "temperature.scorching"),
    MELTING(4, "temperature.melting"),
    VAPORIZING(5, "temperature.vaporizing");

    private final int level;
    private final Component name;
    private final String translationKey;
    PyroTemperatures(int level, String key) {
        this.level = level;
        translationKey = key;
        this.name = Component.translatable(key);
    }

    public Component getName() {
        return name;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public boolean isHotterThan(PyroTemperatures temperature) {
        return level >= temperature.level;
    }
}
