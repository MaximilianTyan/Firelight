package com.github.maximiliantyan.recipe;

import com.github.maximiliantyan.core.PyroStage;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

public interface FlameRecipe extends Recipe<RecipeInput> {
    PyroStage getRequiredFlame();
}
