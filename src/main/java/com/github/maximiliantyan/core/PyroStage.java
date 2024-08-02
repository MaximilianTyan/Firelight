package com.github.maximiliantyan.core;

import com.github.maximiliantyan.Firelight;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PyroStage {
    static class StageCodec implements Codec<PyroStage> {
        @Override
        public <T> DataResult<Pair<PyroStage, T>> decode(DynamicOps<T> ops, T input) {
//            Firelight.LOGGER.info("Decoding codec for input {}", input.getClass().getSimpleName());
            JsonPrimitive json = (JsonPrimitive) input;
            String flameLevel = json.getAsString();

            PyroStage defaultStage = PyroStages.SPARK;

            ResourceLocation stageId = ResourceLocation.tryParse(flameLevel);
            if (stageId == null) {
                ResourceLocation defaultId = PyroStages.getId(defaultStage);
                Firelight.LOGGER.warn("Unknown flame stage id: {}, defaulting to {}", stageId, defaultId);
                stageId = defaultId;
            }

            PyroStage stage = PyroStages.getStage(stageId);
            if (stage == null) {
                Firelight.LOGGER.warn("Unknown flame stage id: {}, defaulting to {}", stage, defaultStage);
                stage = defaultStage;
            }

            return DataResult.success(new Pair<>(stage, input));
        }

        @Override
        public <T> DataResult<T> encode(PyroStage stage, DynamicOps<T> ops, T prefix) {
//            Firelight.LOGGER.info("Encoding codec for stage {} (prefix {})", stage, prefix.getClass().getSimpleName());
            ResourceLocation stageId = PyroStages.getId(stage);
            if (stageId == null) {
                Firelight.LOGGER.warn("Unknown flame stage : {}, defaulting to {}", stage, PyroStages.SPARK);
                stageId = PyroStages.getId(PyroStages.SPARK);
            }
            return DataResult.success(ops.createString(stageId.toString()));
        }
    }

    static class StageStreamCodec implements StreamCodec<RegistryFriendlyByteBuf, PyroStage> {

        @Override
        public @NotNull PyroStage decode(RegistryFriendlyByteBuf buf) {
//            Firelight.LOGGER.info("Decoding stream buffer {}", buf);

            PyroStage defaultStage = PyroStages.SPARK;

            ResourceLocation stageId = buf.readResourceLocation();
            PyroStage stage = PyroStages.getStage(stageId);
            if (stage == null) {
                Firelight.LOGGER.warn("Unknown flame stage id: {}, defaulting to {}", stage, defaultStage);
                stage = defaultStage;
            }

            return stage;
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, PyroStage stage) {
//            Firelight.LOGGER.info("Encoding stream buffer {}", buf);
            ResourceLocation stageId = PyroStages.getId(stage);
            if (stageId == null) {
                Firelight.LOGGER.warn("Unknown flame stage : {}, defaulting to {}", stage, PyroStages.SPARK);
                stageId = PyroStages.getId(PyroStages.SPARK);
            }
            buf.writeResourceLocation(stageId);
        }
    }

    public static final Codec<PyroStage> CODEC = Codec.lazyInitialized(StageCodec::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, PyroStage> STREAM_CODEC = new StageStreamCodec();

    private final PyroTemperatures temperature;
    private final int level;

    public PyroStage(int level, PyroTemperatures temperature) {
        this.temperature = temperature;
        this.level = level;
    }

    @NotNull
    public PyroTemperatures getTemperature() {
        return temperature;
    }

    public int getLevel() {
        return level;
    }

    public boolean isHotterThan(PyroStage stage) {
        return level >= stage.getLevel();
    }

    @Override
    public String toString() {
        return "PyroStage{level=%s, temperature=%s}".formatted(level, temperature);
    }
}
