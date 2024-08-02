package com.github.maximiliantyan.registry;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.entities.FlameHolderBlockEntity;
import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static BlockEntityType<FlameHolderBlockEntity> FLAME_HOLDER = BlockEntityType.Builder.of(FlameHolderBlockEntity::new, ModBlocks.FLAME_HOLDER)
                                                                                                .build();

    private static BlockEntityType<?> registerType(String id, BlockEntityType<?> type) {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                IdUtils.newId("flame_holder"),
                type
        );
    }

    public static void init() {
        Firelight.LOGGER.info("Registering block entity types");
        registerType("flame_holder", FLAME_HOLDER);
    }
}
