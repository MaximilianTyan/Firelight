package com.github.maximiliantyan.recipe.incineration;

import com.github.maximiliantyan.core.FlameStage;
import com.github.maximiliantyan.core.FlameStages;
import com.github.maximiliantyan.recipe.FlameRecipe;
import com.github.maximiliantyan.registry.ModRecipeSerializer;
import com.github.maximiliantyan.registry.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class IncinerationRecipe implements FlameRecipe {

    public static final RecipeType<IncinerationRecipe> TYPE = ModRecipeTypes.INCINERATION;
    public static final RecipeSerializer<IncinerationRecipe> SERIALIZER = ModRecipeSerializer.INCINERATION;

    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final FlameStage stage;

    public IncinerationRecipe(Ingredient ingredient, ItemStack itemStack, FlameStage stage) {
        this.ingredient = ingredient;
        this.result = itemStack;
        this.stage = stage;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(getIngredient());
        return nonNullList;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return this.ingredient.test(recipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public FlameStage getRequiredFlameStage() {
        return stage;
    }
}

