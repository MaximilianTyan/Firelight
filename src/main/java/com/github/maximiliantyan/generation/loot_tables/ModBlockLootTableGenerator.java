package com.github.maximiliantyan.generation.loot_tables;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModBlockLootTableGenerator extends SimpleFabricLootTableProvider {
    public ModBlockLootTableGenerator(
            FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup
    ) {
        super(output, registryLookup, LootContextParamSets.BLOCK);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {

    }
}
