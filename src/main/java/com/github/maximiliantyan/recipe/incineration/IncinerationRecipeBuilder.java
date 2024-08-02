package com.github.maximiliantyan.recipe.incineration;

import com.github.maximiliantyan.core.PyroStage;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

/** Stolen from Applied Energestics Code*/
public class IncinerationRecipeBuilder {
    public static final String FOLDER = "incineration";

    public static void incinerate(RecipeOutput consumer, ResourceLocation id, ItemLike input, ItemLike output, PyroStage requiredStage) {
        consumer.accept(
                id,
                new IncinerationRecipe(Ingredient.of(input), output.asItem().getDefaultInstance(), requiredStage),
                null
        );
    }

    public static void incinerate(RecipeOutput consumer, ResourceLocation id, Ingredient input, ItemLike output, PyroStage requiredStage) {
        consumer.accept(
                id,
                new IncinerationRecipe(input, output.asItem().getDefaultInstance(), requiredStage),
                null
        );
    }

    public static void incinerate(RecipeOutput consumer, ResourceLocation id, TagKey<Item> input, ItemLike output, PyroStage requiredStage) {
        consumer.accept(
                id,
                new IncinerationRecipe(Ingredient.of(input), output.asItem().getDefaultInstance(), requiredStage),
                null
        );
    }

}
