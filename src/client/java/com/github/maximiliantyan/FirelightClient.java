package com.github.maximiliantyan;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirelightClient implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger(Firelight.MOD_ID+"-client");
	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing Firelight Client");
	}
}