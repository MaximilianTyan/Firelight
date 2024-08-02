package com.github.maximiliantyan.core;

import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PyroStages {
    private static final Map<ResourceLocation, PyroStage> ALL = new HashMap<>();

    public static final PyroStage SPARK = addStage("spark", 1, PyroTemperatures.WARM);
    public static final PyroStage FLAME = addStage("flame", 2, PyroTemperatures.HOT);
    public static final PyroStage FIRE = addStage("fire", 3, PyroTemperatures.BURNING);
    public static final PyroStage BRASERO = addStage("brasero", 4, PyroTemperatures.SCORCHING);
    public static final PyroStage BONFIRE = addStage("bonfire", 5, PyroTemperatures.MELTING);
    public static final PyroStage INFERNO = addStage("inferno", 6, PyroTemperatures.VAPORIZING);

    private static PyroStage addStage(String name, int level, PyroTemperatures temp) {
        PyroStage stage = new PyroStage(level, temp);
        ALL.put(IdUtils.newId(name), stage);
        return stage;
    }

    @Nullable
    public static PyroStage getStage(int level) {
        for (PyroStage stage : ALL.values()) {
            if (stage.getLevel() == level) {
                return stage;
            }
        }
        return null;
    }

    @Nullable
    public static PyroStage getStage(ResourceLocation id) {
        if (ALL.containsKey(id)) {
            return ALL.get(id);
        }
        return null;
    }

    @Nullable
    public static ResourceLocation getId(PyroStage stage) {
        for (ResourceLocation id : ALL.keySet()) {
            if (ALL.get(id) == stage) {
                return id;
            }
        }
        return null;
    }

    public static int getMaxLevel() {
        return ALL.values()
                  .stream()
                  .map(PyroStage::getLevel)
                  .max(Integer::compareTo)
                  .orElse(0);
    }

    public static int getMinLevel() {
        return ALL.values()
                  .stream()
                  .map(PyroStage::getLevel)
                  .min(Integer::compareTo)
                  .orElse(0);
    }
}
