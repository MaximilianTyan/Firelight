package com.github.maximiliantyan;

import com.github.maximiliantyan.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Firelight implements ModInitializer {
	public static final String MOD_ID = "firelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing Firelight");
		ModRecipeTypes.init();
		ModRecipeSerializer.init();
		ModBlockEntityTypes.init();
		ModBlocks.init();
		ModItems.init();
		ModTab.init();
	}
}