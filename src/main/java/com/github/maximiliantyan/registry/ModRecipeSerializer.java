package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipe;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipeSerializer;
import com.github.maximiliantyan.recipe.purification.PurificationRecipe;
import com.github.maximiliantyan.recipe.purification.PurificationRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializer {

    public static final RecipeSerializer<PurificationRecipe> PURIFICATION = new PurificationRecipeSerializer();
    public static final RecipeSerializer<IncinerationRecipe> INCINERATION = new IncinerationRecipeSerializer();

    public static void registerSerializer(String id, RecipeSerializer<?> type) {
        Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                Firelight.newId( id),
                type
        );
    }

    public static void init() {
        Firelight.LOGGER.info("Registering recipe serializers");
        registerSerializer("purification", PURIFICATION);
        registerSerializer("incineration", INCINERATION);
    }
}
