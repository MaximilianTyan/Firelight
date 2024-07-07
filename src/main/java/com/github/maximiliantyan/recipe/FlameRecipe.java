package com.github.maximiliantyan.recipe;

import com.github.maximiliantyan.core.FlameStage;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

public interface FlameRecipe extends Recipe<RecipeInput> {
    public FlameStage getRequiredFlameStage();
}
