package com.github.maximiliantyan.items;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.PyroStage;
import com.github.maximiliantyan.recipe.BlockItemConverter;
import com.github.maximiliantyan.recipe.RecipeAccessor;
import com.github.maximiliantyan.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlameItem extends Item {
    private final PyroStage stage;

    public FlameItem(PyroStage stage, Item.Properties properties) {
        super(properties);
        this.stage = stage;
    }

    public PyroStage getPyroStage() {
        return stage;
    }

    @Override
    @NotNull
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockPos blockPos = useOnContext.getClickedPos();
        Block block = level.getBlockState(blockPos).getBlock();
        FluidState fluid = useOnContext.getLevel().getFluidState(blockPos);

        // Handle fluids
        Item inputItem;
        if (!fluid.isEmpty() && !fluid.is(Fluids.EMPTY)) {
            inputItem = fluid.getType().getBucket();
            if (inputItem.equals(Items.AIR)) {
                Firelight.LOGGER.warn("Target fluid bucket item not found: {}", fluid.getType());
                onUseFail(level, player);
                return InteractionResult.FAIL;
            }
        } else {
            inputItem = block.asItem();
            if (inputItem.equals(Items.AIR)) {
                Firelight.LOGGER.warn("Target block item not found: {}", block);
                onUseFail(level, player);
                return InteractionResult.FAIL;
            }
        }

        // Recipe lookup
        List<ItemStack> recipeResults = new ArrayList<>();
        recipeResults.add(RecipeAccessor.getRecipeResult(
                ModRecipeTypes.INCINERATION,
                stage,
                inputItem.getDefaultInstance(),
                level
        ));
        recipeResults.add(RecipeAccessor.getRecipeResult(
                ModRecipeTypes.PURIFICATION,
                stage,
                inputItem.getDefaultInstance(),
                level
        ));

        // Results checks
        List<ItemStack> recipeMatches = recipeResults.stream().filter(stack -> !stack.isEmpty()).toList();
        if (recipeMatches.isEmpty()) {
            onUseFail(level, player);
            return InteractionResult.FAIL;
        }
        if (recipeMatches.size() > 1) {
            Firelight.LOGGER.warn("Overlaying recipes detected: {}", recipeMatches);
        }
        ItemStack resultStack = recipeMatches.getFirst();
        BlockState resultBlock = BlockItemConverter.itemToBlock(resultStack).defaultBlockState();

        Firelight.LOGGER.info("Matching result item {} block {}", resultStack, resultBlock);

        if (!resultStack.isEmpty() && resultBlock.isAir())
        {
            Firelight.LOGGER.info("Dropping item {}", resultStack);
            ItemEntity droppedItem = new ItemEntity(
                    level,
                    blockPos.getCenter().x,
                    blockPos.getCenter().y,
                    blockPos.getCenter().z,
                    resultStack
            );            droppedItem.setPickUpDelay(0);
            droppedItem.setDeltaMovement(0, 0.2F, 0);
            level.addFreshEntity(droppedItem);
        }

        level.setBlockAndUpdate(blockPos, resultBlock);
        onUseSuccess(level, player);
        Firelight.LOGGER.warn("Replaced {} with {}", block, resultBlock);
        onUseFail(level, player);
        return InteractionResult.SUCCESS;
    }

    public void onUseFail(Level level, Player player) {
        if (!level.isClientSide()) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    level.random.triangle(player.getX(), 0.5),
                    level.random.triangle(player.getY(), 0.2),
                    level.random.triangle(player.getZ(), 0.5),
                    level.random.triangle(0, 0.1),
                    0.2,
                    level.random.triangle(0, 0.1)
            );
        }
        player.playSound(SoundEvents.FIRE_EXTINGUISH, 0.5f, 1.5f);
    }

    public void onUseSuccess(Level level, Player player) {
        if (!level.isClientSide()) {
            return;
        }
        for (int i = 0; i < 8; i++) {
            level.addParticle(
                    ParticleTypes.FLAME,
                    level.random.triangle(player.getX(), 0.5),
                    level.random.triangle(player.getY(), 0.2),
                    level.random.triangle(player.getZ(), 0.5),
                    level.random.triangle(0, 0.1),
                    0.2,
                    level.random.triangle(0, 0.1)
            );
        }
        player.playSound(SoundEvents.FIRECHARGE_USE, 0.5f, 0.5f);
    }
}
