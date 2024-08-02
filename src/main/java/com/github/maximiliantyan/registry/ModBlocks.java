package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.blocks.FlameHolderBlock;
import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

    public static final Block FLAME_HOLDER = new FlameHolderBlock(
            BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE)
                                     .noOcclusion()
                                     .lightLevel(FlameHolderBlock::getLightingLevel)
    );

    private static void registerBlock(String id, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, IdUtils.newId(id), block);
    }

    public static void init() {
        Firelight.LOGGER.info("Registering blocks");
        registerBlock("flame_holder", FLAME_HOLDER);
    }
}
