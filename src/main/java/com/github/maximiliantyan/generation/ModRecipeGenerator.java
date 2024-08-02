package com.github.maximiliantyan.generation;

import com.github.maximiliantyan.generation.recipes.CraftingRecipeGenerator;
import com.github.maximiliantyan.generation.recipes.IncinerationRecipeGenerator;
import com.github.maximiliantyan.generation.recipes.PurificationRecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * Implement this method and then use the range of methods in {@link RecipeProvider} or from one of the recipe json factories such as {@link ShapedRecipeBuilder} or {@link ShapelessRecipeBuilder}.
     *
     * @param exporter
     */
    @Override
    public void buildRecipes(RecipeOutput exporter) {
        CraftingRecipeGenerator.buildRecipes(exporter);
        IncinerationRecipeGenerator.buildRecipes(exporter);
        PurificationRecipeGenerator.buildRecipes(exporter);
    }


}
