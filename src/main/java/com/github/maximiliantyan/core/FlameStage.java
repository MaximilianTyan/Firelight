package com.github.maximiliantyan.core;

import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FlameStage {

    class StageCodec implements Codec<FlameStage> {
        @Override
        public <T> DataResult<Pair<FlameStage, T>> decode(DynamicOps<T> ops, T input) {
            JsonPrimitive json = (JsonPrimitive) input;
            ResourceLocation key = ResourceLocation.tryParse(json.getAsString());
            return DataResult.success(new Pair<>(FlameStages.getStage(key), input));
        }

        @Override
        public <T> DataResult<T> encode(FlameStage stage, DynamicOps<T> ops, T prefix) {
            return DataResult.success(ops.createString(stage.getId().toString()));
        }
    }

    class StageStreamCodec implements StreamCodec<RegistryFriendlyByteBuf, FlameStage> {

        @Override
        public FlameStage decode(RegistryFriendlyByteBuf buf) {
            ResourceLocation key = buf.readResourceLocation();
            return FlameStages.getStage(key);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, FlameStage stage) {
            buf.writeResourceLocation(stage.getId());
        }
    }

    public static final StageCodec CODEC = new StageCodec();
    public static final StageStreamCodec STREAM_CODEC = new StageStreamCodec();

    @NotNull
    public FlameTemperatures getTemperature();

    @NotNull
    public ResourceLocation getId();

    @NotNull
    public Component getName();

    public boolean canUpgrade();

    @Nullable
    public FlameStage getNextStage();
}
