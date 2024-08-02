package com.github.maximiliantyan.utils;

import net.minecraft.core.HolderLookup;

import java.util.stream.Stream;

public class LookupProvider {
    public static final HolderLookup.Provider EMPTY = HolderLookup.Provider.create(Stream.empty());
}
