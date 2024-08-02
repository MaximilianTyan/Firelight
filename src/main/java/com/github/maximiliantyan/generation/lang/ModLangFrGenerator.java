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

public class ModLangFrGenerator extends FabricLanguageProvider {
    public static final String LANG_CODE = "fr";

    public ModLangFrGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
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
        translationBuilder.add(ModItems.SPARK, "Étincelle");
        translationBuilder.add(ModItems.FLAME, "Flamme");
        translationBuilder.add(ModItems.FIRE, "Feu");
        translationBuilder.add(ModItems.BRASERO, "Brasero");
        translationBuilder.add(ModItems.BONFIRE, "Bûcher");
        translationBuilder.add(ModItems.INFERNO, "Infernal");
        translationBuilder.add(ModItems.ASH, "Cendre");

        // Blocks
        translationBuilder.add(ModBlocks.FLAME_HOLDER, "Porte Flamme");

        // Internals
        translationBuilder.add(PyroTemperatures.WARM.getTranslationKey(), "Tiède");
        translationBuilder.add(PyroTemperatures.HOT.getTranslationKey(), "Chaud");
        translationBuilder.add(PyroTemperatures.BURNING.getTranslationKey(), "Brûlant");
        translationBuilder.add(PyroTemperatures.SCORCHING.getTranslationKey(), "Torride");
        translationBuilder.add(PyroTemperatures.MELTING.getTranslationKey(), "Fondant");
        translationBuilder.add(PyroTemperatures.VAPORIZING.getTranslationKey(), "Vaporisant");

        // Recipes
        translationBuilder.add(IncinerationRecipe.getTranslationKey(), "Incinération");
        translationBuilder.add(PurificationRecipe.getTranslationKey(), "Purification");
    }
}
