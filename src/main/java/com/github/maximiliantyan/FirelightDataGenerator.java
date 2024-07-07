package com.github.maximiliantyan;

import com.github.maximiliantyan.generation.ModModelGenerator;
import com.github.maximiliantyan.generation.loot_tables.ModBlockLootTableGenerator;
import com.github.maximiliantyan.generation.ModRecipeGenerator;
import com.github.maximiliantyan.generation.lang.ModLangEnGenerator;
import com.github.maximiliantyan.generation.lang.ModLangFrGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FirelightDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		Firelight.LOGGER.info("Generating Firelight Data");
		FabricDataGenerator.Pack pack = generator.createPack();

		// Other providers
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModModelGenerator::new);

		// Loot tables
		pack.addProvider(ModBlockLootTableGenerator::new);

		// Lang
		pack.addProvider(ModLangEnGenerator::new);
		pack.addProvider(ModLangFrGenerator::new);
	}
}
