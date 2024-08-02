package com.github.maximiliantyan.recipe.purification;

import com.github.maximiliantyan.core.PyroStage;
import com.github.maximiliantyan.recipe.FlameRecipe;
import com.github.maximiliantyan.registry.ModRecipeSerializer;
import com.github.maximiliantyan.registry.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PurificationRecipe implements FlameRecipe {

    public static final RecipeType<PurificationRecipe> TYPE = ModRecipeTypes.PURIFICATION;
    public static final RecipeSerializer<PurificationRecipe> SERIALIZER = ModRecipeSerializer.PURIFICATION;

    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final PyroStage stage;

    public PurificationRecipe(Ingredient ingredient, ItemStack itemStack, PyroStage stage) {
        this.ingredient = ingredient;
        this.result = itemStack;
        this.stage = stage;
    }

    @Override
    public @NotNull RecipeType<PurificationRecipe> getType() {
        return TYPE;
    }

    @NotNull
    @Override
    public RecipeSerializer<PurificationRecipe> getSerializer() {
        return SERIALIZER;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    public static String getTranslationKey() {
        return "recipe.purification";
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.ingredient);
        return nonNullList;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return this.ingredient.test(recipeInput.getItem(0));
    }

    @NotNull
    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Override
    public PyroStage getRequiredFlame() {
        return stage;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}

