package com.github.maximiliantyan.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class BlockItemConverter {

    public static Block itemToBlock(ItemStack stack) {
        return itemToBlock(stack.getItem());
    }

    public static Block itemToBlock(Item item) {
        if (item == Items.AIR) return Blocks.AIR;

        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock();
        } else if (item instanceof BucketItem bucketItem) {
            List<Fluid> matchingFluids = BuiltInRegistries.FLUID.stream()
                                                                .filter(fluid -> fluid.getBucket()
                                                                                      .equals(bucketItem))
                                                                .toList();
            if (matchingFluids.isEmpty()) {
                return Blocks.AIR;
            }
            return matchingFluids.getFirst()
                                 .defaultFluidState()
                                 .createLegacyBlock()
                                 .getBlock();
        } else {
            ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(item);
            // Defaults to AIR
            return BuiltInRegistries.BLOCK.get(itemKey);
        }
    }

    public static Item blockToItem(BlockState blockState) {
        return blockToItem(blockState.getBlock());
    }

    public static Item blockToItem(Block block) {
        if (block == Blocks.AIR) return Items.AIR;

        if (block.asItem() != Items.AIR) return block.asItem();

        ResourceLocation itemKey = BuiltInRegistries.BLOCK.getKey(block);
        // Defaults to AIR
        return BuiltInRegistries.ITEM.get(itemKey);
    }
}
