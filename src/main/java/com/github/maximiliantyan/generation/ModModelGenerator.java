package com.github.maximiliantyan.generation;

import com.github.maximiliantyan.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

public class ModModelGenerator extends FabricModelProvider {

    public ModModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        generators.generateFlatItem(ModItems.SPARK, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(ModItems.EMBER, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(ModItems.KINDLE, ModelTemplates.FLAT_ITEM);
    }
}
