package com.github.maximiliantyan.generation.lang;

import com.github.maximiliantyan.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLangEnGenerator extends FabricLanguageProvider {
    public static final String LANG_CODE = "en";

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
        translationBuilder.add(ModItems.SPARK, "Spark");
        translationBuilder.add(ModItems.EMBER, "Ember");
        translationBuilder.add(ModItems.KINDLE, "Kindle");
    }
}
