package com.github.maximiliantyan.core;

import com.github.maximiliantyan.core.stages.EmberStage;
import com.github.maximiliantyan.core.stages.KindleStage;
import com.github.maximiliantyan.core.stages.SparkStage;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class FlameStages {
    public static final FlameStage SPARK = new SparkStage();
    public static final FlameStage EMBER = new EmberStage();
    public static final FlameStage KINDLE = new KindleStage();

    public static final FlameStage[] ALL = new FlameStage[]{SPARK, EMBER};

    @Nullable
    public static FlameStage getStage(ResourceLocation key) {
        for (FlameStage stage : ALL) {
            if (stage.getId() == key) {
                return stage;
            }
        }
        return null;
    }
}
