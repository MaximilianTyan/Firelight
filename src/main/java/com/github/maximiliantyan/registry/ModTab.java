package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;

public class ModTab {

    public static final CreativeModeTab TAB = FabricItemGroup.builder()
                                                             .title(Component.literal("Firelight"))
                                                             .icon(() -> new ItemStack(ModItems.SPARK))
                                                             .displayItems(ModTab::registerItems)
                                                             .build();

    public static  ItemLike[] ENTRIES = {
        ModItems.SPARK,
    };

    private static void registerItems(CreativeModeTab.ItemDisplayParameters dispParam, CreativeModeTab.Output output) {
        Firelight.LOGGER.info("Registering tab entries");
        for (ItemLike entry : ENTRIES) {
            output.accept(entry);
        }
    }
}
