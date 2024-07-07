package com.github.maximiliantyan;

import com.github.maximiliantyan.registry.ModItems;
import com.github.maximiliantyan.registry.ModRecipeSerializer;
import com.github.maximiliantyan.registry.ModRecipeTypes;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Firelight implements ModInitializer {
	public static final String MOD_ID = "firelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ResourceLocation newId(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing Firelight");
		ModRecipeTypes.init();
		ModRecipeSerializer.init();
		ModItems.init();
	}
}