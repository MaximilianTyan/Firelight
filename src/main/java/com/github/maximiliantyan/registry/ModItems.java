package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.PyroStages;
import com.github.maximiliantyan.items.FlameItem;
import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {

    public static final Item SPARK = new FlameItem(
            PyroStages.SPARK,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.COMMON)
    );
    public static final Item FLAME = new FlameItem(
            PyroStages.FLAME,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.COMMON)
    );

    public static final Item FIRE = new FlameItem(
            PyroStages.FIRE,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.UNCOMMON)
    );

    public static final Item BRASERO = new FlameItem(
            PyroStages.BRASERO,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.RARE)
    );

    public static final Item BONFIRE = new FlameItem(
            PyroStages.BONFIRE,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.RARE)
    );

    public static final Item INFERNO = new FlameItem(
            PyroStages.INFERNO,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.EPIC)
    );

    public static final Item ASH = new Item(new Item.Properties());

    // Block items
    public static final Item FLAME_HOLDER = new BlockItem(ModBlocks.FLAME_HOLDER, new Item.Properties());

    private static void registerItem(String id, Item item) {
        Registry.register(BuiltInRegistries.ITEM, IdUtils.newId( id), item);
    }

    public static void init() {
        Firelight.LOGGER.info("Registering items");

        // Pure Items
        registerItem("spark", SPARK);
        registerItem("flame", FLAME);
        registerItem("fire", FIRE);
        registerItem("brasero", BRASERO);
        registerItem("bonfire", BONFIRE);
        registerItem("inferno", INFERNO);
        registerItem("ash", ASH);

        // Block Items
        registerItem("flame_holder", FLAME_HOLDER);
    }
}
