package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.FirelightClient;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class ModBlockRenderMap {
    public static void init() {
        FirelightClient.LOGGER.info("Registering mod Render Blocks");
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLAME_HOLDER, RenderType.cutout());
    }
}
