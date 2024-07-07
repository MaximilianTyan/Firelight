package com.github.maximiliantyan.generation;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.FlameStages;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipeBuilder;
import com.github.maximiliantyan.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SPARK)
                              .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
                              .requires(Items.GUNPOWDER)
                              .requires(Items.GOLD_NUGGET)
                              .unlockedBy("gold_nugget", FabricRecipeProvider.has(Items.GOLD_NUGGET))
                              .save(exporter, Firelight.newId("spark"));

        buildSparkRecipes(exporter);
        buildEmberRecipes(exporter);
    }

    public void buildSparkRecipes(RecipeOutput exporter) {
        IncinerationRecipeBuilder.incinerate(
                exporter,
                Firelight.newId( "torch"),
                Items.STICK,
                Items.TORCH,
                FlameStages.SPARK
        );
    }

    public void buildEmberRecipes(RecipeOutput exporter) {
        IncinerationRecipeBuilder.incinerate(
                exporter,
                Firelight.newId( "magma_block"),
                Items.STONE,
                Items.MAGMA_BLOCK,
                FlameStages.EMBER
        );

        IncinerationRecipeBuilder.incinerate(
                exporter,
                Firelight.newId( "lava"),
                Items.MAGMA_BLOCK,
                Items.LAVA_BUCKET,
                FlameStages.KINDLE
        );
    }
}
