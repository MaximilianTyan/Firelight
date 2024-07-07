package com.github.maximiliantyan.items;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.FlameStage;
import com.github.maximiliantyan.core.FlameStages;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipe;
import com.github.maximiliantyan.recipe.purification.PurificationRecipe;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class FlameItem extends Item {
    private FlameStage stage;

    private static final RecipeManager.CachedCheck<RecipeInput, IncinerationRecipe> quickIncinerateCheck =
            RecipeManager.createCheck(IncinerationRecipe.TYPE);
    private static final RecipeManager.CachedCheck<RecipeInput, PurificationRecipe> quickPurifyCheck =
            RecipeManager.createCheck(PurificationRecipe.TYPE);


    public FlameItem(Item.Properties properties) {
        this(FlameStages.SPARK, properties);
    }

    public FlameItem(FlameStage stage, Item.Properties properties) {
        super(properties);
        this.stage = stage;
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockState block = level.getBlockState(useOnContext.getClickedPos());

        RecipeInput input = new SingleRecipeInput(block.getBlock().asItem().getDefaultInstance());

        Optional<RecipeHolder<IncinerationRecipe>> matchingIncinerateRecipe = quickIncinerateCheck.getRecipeFor(input, level);
        if (matchingIncinerateRecipe.isPresent()) {
            Firelight.LOGGER.info("Matching incinerating recipe found: {}", matchingIncinerateRecipe.get().value());
            return InteractionResult.SUCCESS;
        }

        Optional<RecipeHolder<PurificationRecipe>> matchingPurifyRecipe = quickPurifyCheck.getRecipeFor(input, level);
        if (matchingPurifyRecipe.isPresent()) {
            Firelight.LOGGER.info("Matching purify recipe found: {}", matchingPurifyRecipe.get().value());
            return InteractionResult.SUCCESS;
        }

        onUseFailed(useOnContext);
        return InteractionResult.PASS;
    }

    public void onUseFailed(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockState block = level.getBlockState(useOnContext.getClickedPos());

        Firelight.LOGGER.info("FlameItem used on {} by {} failed", block, player.getName());

        for (int i = 0; i < 8; i++) {
            level.addParticle(
                    ParticleTypes.ASH,
                    level.random.triangle(player.getX(), 0.2),
                    level.random.triangle(player.getY(), 0.2),
                    level.random.triangle(player.getZ(), 0.2),
                    level.random.triangle(0, 0.1),
                    0.1,
                    level.random.triangle(0, 0.1)
            );
        }
    }

    public boolean upgrade() {
        if (!stage.canUpgrade()) {
            return false;
        }

        if (stage.getNextStage() == null) {
            return false;
        }

        stage = stage.getNextStage();
        return true;
    }
}
