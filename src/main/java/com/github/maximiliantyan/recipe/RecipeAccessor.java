package com.github.maximiliantyan.recipe;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.PyroStage;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipe;
import com.github.maximiliantyan.recipe.purification.PurificationRecipe;
import com.github.maximiliantyan.utils.LookupProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Optional;

public class RecipeAccessor {
    private static final Map<RecipeType<? extends FlameRecipe>, RecipeManager.CachedCheck<RecipeInput, ? extends FlameRecipe>>
            RECIPES = Map.of(
            IncinerationRecipe.TYPE, RecipeManager.createCheck(IncinerationRecipe.TYPE),
            PurificationRecipe.TYPE, RecipeManager.createCheck(PurificationRecipe.TYPE)
    );

    public static ItemStack getRecipeResult(RecipeType<?> recipeType, PyroStage stage, ItemStack item, Level level) {
        if (stage == null) return ItemStack.EMPTY;
        RecipeInput input = new SingleRecipeInput(item);

        if (!RECIPES.containsKey(recipeType)) {
            Firelight.LOGGER.info("Unknown recipe type: {}", recipeType);
            return ItemStack.EMPTY;
        }
        Optional<? extends RecipeHolder<? extends FlameRecipe>> matchingRecipe =
                RECIPES.get(recipeType).getRecipeFor(input, level);
        if (matchingRecipe.isEmpty()) {
            return ItemStack.EMPTY;
        }

        RecipeHolder<? extends FlameRecipe> recipeHolder = matchingRecipe.get();

        PyroStage requiredStage = recipeHolder.value().getRequiredFlame();
        if (!stage.isHotterThan(requiredStage)) {
//            Firelight.LOGGER.info("Wrong flame stageEnum: upgrade recipe requires at least {}", requiredStage);
            return ItemStack.EMPTY;
        }

        return recipeHolder.value().getResultItem(LookupProvider.EMPTY);
    }
}
