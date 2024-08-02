package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipe;
import com.github.maximiliantyan.recipe.purification.PurificationRecipe;
import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {

    public static RecipeType<IncinerationRecipe> INCINERATION = RecipeType.register("incineration");
    public static RecipeType<PurificationRecipe> PURIFICATION = RecipeType.register("purification");

    public static void registerType(String id, RecipeType<?> type) {
        Registry.register(
                BuiltInRegistries.RECIPE_TYPE,
                IdUtils.newId( id),
                type
        );
    }

    public static void init() {
        Firelight.LOGGER.info("Registering recipe types");
        registerType("incineration", INCINERATION);
        registerType("purification", PURIFICATION);
    }
}
