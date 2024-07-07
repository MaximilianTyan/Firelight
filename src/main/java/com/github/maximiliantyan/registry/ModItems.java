package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.FlameStages;
import com.github.maximiliantyan.items.FlameItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {

    public static final Item SPARK = new FlameItem(
            FlameStages.SPARK,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.COMMON)
    );
    public static final Item EMBER = new FlameItem(
            FlameStages.EMBER,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.COMMON)
    );

    public static final Item KINDLE = new FlameItem(
            FlameStages.KINDLE,
            new Item.Properties().stacksTo(1)
                                 .fireResistant()
                                 .rarity(Rarity.COMMON)
    );

    private static void registerItem(String id, Item item) {
        Registry.register(BuiltInRegistries.ITEM, Firelight.newId( id), item);
    }

    public static void init() {
        Firelight.LOGGER.info("Registering items");
        registerItem("spark", SPARK);
        registerItem("ember", EMBER);
        registerItem("kindle", KINDLE);
    }
}
