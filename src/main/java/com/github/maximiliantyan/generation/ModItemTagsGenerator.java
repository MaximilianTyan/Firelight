package com.github.maximiliantyan.generation;

import com.github.maximiliantyan.registry.ModItemTags;
import com.github.maximiliantyan.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsGenerator extends ItemTagProvider {

    /**
     * Construct an {@link ItemTagProvider} tag provider <b>without</b> an associated {@link BlockTagProvider} tag provider.
     *
     * @param output            The {@link FabricDataOutput} instance
     * @param completableFuture
     */
    public ModItemTagsGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    /**
     * Implement this method and then use {@link FabricTagProvider#tag} to get and register new tag builders.
     *
     * @param wrapperLookup
     */
    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(ModItemTags.FLAMES)
                .add(ModItems.SPARK)
                .add((ModItems.FLAME))
                .add((ModItems.FIRE))
                .add((ModItems.BRASERO))
                .add((ModItems.BONFIRE))
                .add((ModItems.INFERNO));

        getOrCreateTagBuilder(ModItemTags.SMELT_INTO_CHARCOAL)
                .addOptionalTag(ItemTags.FLOWERS)
                .add(Items.FERN)
                .add(Items.TALL_GRASS)
                .add(Items.SHORT_GRASS)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.TORCHFLOWER_SEEDS)
                .add(Items.PITCHER_POD)
                .add(Items.WHEAT_SEEDS)
                .add(Items.WHEAT)
                .add(Items.PUMPKIN_SEEDS);
    }
}
