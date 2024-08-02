package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags { // No associated registry
    public static final TagKey<Item> FLAMES = TagKey.create(Registries.ITEM, IdUtils.newId("flames"));
    public static final TagKey<Item> SMELT_INTO_CHARCOAL = TagKey.create(Registries.ITEM, IdUtils.newId("smelt_into_charcoal"));
}
