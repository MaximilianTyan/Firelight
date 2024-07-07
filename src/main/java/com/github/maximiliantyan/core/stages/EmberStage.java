package com.github.maximiliantyan.core.stages;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.FlameStage;
import com.github.maximiliantyan.core.FlameStages;
import com.github.maximiliantyan.core.FlameTemperatures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmberStage implements FlameStage {
    @Override
    public @NotNull FlameTemperatures getTemperature() {
        return FlameTemperatures.WARM;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return Firelight.newId("ember");
    }

    @Override
    public @NotNull Component getName() {
        return Component.literal("Ember");
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public @Nullable FlameStage getNextStage() {
        return FlameStages.KINDLE;
    }
}
