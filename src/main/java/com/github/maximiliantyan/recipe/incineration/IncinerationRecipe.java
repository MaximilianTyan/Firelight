package com.github.maximiliantyan.recipe.incineration;

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

public class IncinerationRecipe implements FlameRecipe {

    public static final RecipeType<IncinerationRecipe> TYPE = ModRecipeTypes.INCINERATION;
    public static final RecipeSerializer<IncinerationRecipe> SERIALIZER = ModRecipeSerializer.INCINERATION;

    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final PyroStage flame;

    public IncinerationRecipe(Ingredient ingredient, ItemStack itemStack, PyroStage stage) {
        this.ingredient = ingredient;
        this.result = itemStack;
        this.flame = stage;
    }

    @Override
    @NotNull
    public RecipeType<IncinerationRecipe> getType() {
        return TYPE;
    }

    @Override
    @NotNull
    public RecipeSerializer<IncinerationRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    @NotNull
    public  ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.result;
    }

    public static String getTranslationKey() {
        return "recipe.incineration";
    }

    @Override
    @NotNull
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

    public PyroStage getRequiredFlame() {
        return flame;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}

