package com.github.maximiliantyan.generation.lang;

import com.github.maximiliantyan.core.PyroTemperatures;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipe;
import com.github.maximiliantyan.recipe.purification.PurificationRecipe;
import com.github.maximiliantyan.registry.ModBlocks;
import com.github.maximiliantyan.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLangEnGenerator extends FabricLanguageProvider {
    public static final String LANG_CODE = "en_us";

    public ModLangEnGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, LANG_CODE, registryLookup);
    }

    /**
     * Implement this method to register languages.
     *
     * <p>Call {@link TranslationBuilder#add(String, String)} to add a translation.
     *
     * @param registryLookup
     * @param translationBuilder
     */
    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        // Items
        translationBuilder.add(ModItems.SPARK, "Spark");
        translationBuilder.add(ModItems.FLAME, "Flame");
        translationBuilder.add(ModItems.FIRE, "Fire");
        translationBuilder.add(ModItems.BRASERO, "Brasero");
        translationBuilder.add(ModItems.BONFIRE, "Bonfire");
        translationBuilder.add(ModItems.INFERNO, "Inferno");
        translationBuilder.add(ModItems.ASH, "Ash");

        // Blocks
        translationBuilder.add(ModBlocks.FLAME_HOLDER, "Flame Holder");

        // Internals
        translationBuilder.add(PyroTemperatures.WARM.getTranslationKey(), "Warm");
        translationBuilder.add(PyroTemperatures.HOT.getTranslationKey(), "Hot");
        translationBuilder.add(PyroTemperatures.BURNING.getTranslationKey(), "Burning");
        translationBuilder.add(PyroTemperatures.SCORCHING.getTranslationKey(), "Scorching");
        translationBuilder.add(PyroTemperatures.MELTING.getTranslationKey(), "Melting");
        translationBuilder.add(PyroTemperatures.VAPORIZING.getTranslationKey(), "Vaporizing");

        // Recipes
        translationBuilder.add(IncinerationRecipe.getTranslationKey(), "Incineration");
        translationBuilder.add(PurificationRecipe.getTranslationKey(), "Purification");
    }
}
