package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.utils.IdUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ModTab {

    public static final CreativeModeTab TAB = FabricItemGroup.builder()
                                                             .title(Component.literal("Firelight"))
                                                             .icon(() -> new ItemStack(ModItems.FLAME))
                                                             .displayItems(ModTab::registerItems)
                                                             .build();

    public static ItemLike[] ENTRIES = {
            ModItems.SPARK,
            ModItems.FLAME,
            ModItems.FIRE,
            ModItems.BRASERO,
            ModItems.BONFIRE,
            ModItems.INFERNO,
            ModItems.ASH,

            ModBlocks.FLAME_HOLDER
    };

    private static void registerItems(CreativeModeTab.ItemDisplayParameters dispParam, CreativeModeTab.Output output) {
        Firelight.LOGGER.info("Registering tab entries");
        for (ItemLike entry : ENTRIES) {
            output.accept(entry);
        }
    }

    public static void init() {
        Firelight.LOGGER.info("Registering creative tab");
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, IdUtils.newId("tab"), TAB);
    }
}
