package com.github.maximiliantyan.utils;

import com.github.maximiliantyan.Firelight;
import net.minecraft.resources.ResourceLocation;

public class IdUtils {
    public static ResourceLocation newId(String id) {
        return ResourceLocation.fromNamespaceAndPath(Firelight.MOD_ID, id);
    }

    public static ResourceLocation newRecipeId(String category, String id) {
        return ResourceLocation.fromNamespaceAndPath(Firelight.MOD_ID, category + '/' + id);
    }
}
