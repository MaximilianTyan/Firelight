package com.github.maximiliantyan.recipe.purification;

import com.github.maximiliantyan.core.PyroStage;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class PurificationRecipeBuilder {
    public static final String CATEGORY = "purification";

    public static void incinerate(RecipeOutput consumer, ResourceLocation id, ItemLike input, ItemLike output, PyroStage requiredStage) {
        consumer.accept(
                id,
                new PurificationRecipe(Ingredient.of(input), output.asItem().getDefaultInstance(), requiredStage),
                null
        );
    }

    public static void incinerate(RecipeOutput consumer, ResourceLocation id, Ingredient input, ItemLike output, PyroStage requiredStage) {
        consumer.accept(
                id,
                new PurificationRecipe(input, output.asItem().getDefaultInstance(), requiredStage),
                null
        );
    }

    public static void incinerate(RecipeOutput consumer, ResourceLocation id, TagKey<Item> input, ItemLike output, PyroStage requiredStage) {
        consumer.accept(
                id,
                new PurificationRecipe(Ingredient.of(input), output.asItem().getDefaultInstance(), requiredStage),
                null
        );
    }
}
