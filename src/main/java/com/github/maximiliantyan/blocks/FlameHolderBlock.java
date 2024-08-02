package com.github.maximiliantyan.blocks;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.core.PyroStages;
import com.github.maximiliantyan.entities.FlameHolderBlockEntity;
import com.github.maximiliantyan.items.FlameItem;
import com.github.maximiliantyan.recipe.BlockItemConverter;
import com.github.maximiliantyan.recipe.RecipeAccessor;
import com.github.maximiliantyan.registry.ModRecipeTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlameHolderBlock extends BaseEntityBlock {
    public static final MapCodec<FlameHolderBlock> CODEC = simpleCodec(FlameHolderBlock::new);
    public static final BooleanProperty HOLDING_FLAME = BooleanProperty.create("holding");
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public static final IntegerProperty FLAME_LEVEL = IntegerProperty.create(
            "level",
            PyroStages.getMinLevel(),
            PyroStages.getMaxLevel()
    );

    public FlameHolderBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                                    .setValue(HOLDING_FLAME, false)
                                    .setValue(FLAME_LEVEL, PyroStages.getMinLevel())
                                    .setValue(TRIGGERED, false)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FlameHolderBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HOLDING_FLAME, TRIGGERED, FLAME_LEVEL);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        tryIncinerate(level, pos, state);
    }

    public static void tryIncinerate(Level level, BlockPos blockPos, BlockState blockState) {
        if (!(level.getBlockEntity(blockPos) instanceof FlameHolderBlockEntity blockEntity)) {
            Firelight.LOGGER.warn("Ignoring incinerate attempt without matching block entity at {}: {}", blockPos, level.getBlockEntity(blockPos));
            return;
        }

        BlockPos topBlockPos = blockPos.above();
        BlockState topBlock = level.getBlockState(topBlockPos);

        ItemStack blockItem = BlockItemConverter.blockToItem(topBlock).getDefaultInstance();
        if (blockItem.isEmpty()) {
            onConversionFail(level, topBlockPos);
            return;
        }

        FlameItem flame = (FlameItem) blockEntity.getTheItem().getItem();
        ItemStack resultStack = RecipeAccessor.getRecipeResult(
                ModRecipeTypes.INCINERATION,
                flame.getPyroStage(),
                blockItem,
                level
        );
        if (resultStack.isEmpty()) {
            onConversionFail(level, topBlockPos);
            return;
        }

        BlockState resultBlock = BlockItemConverter.itemToBlock(resultStack).defaultBlockState();

        level.setBlockAndUpdate(topBlockPos, resultBlock);
        level.gameEvent(GameEvent.BLOCK_CHANGE, topBlockPos, GameEvent.Context.of(blockState));

        if (!resultStack.isEmpty() && resultBlock.isAir()) {
            ItemEntity droppedItem = new ItemEntity(
                    level,
                    topBlockPos.getCenter().x,
                    topBlockPos.getCenter().y,
                    topBlockPos.getCenter().z,
                    resultStack
            );
            droppedItem.setPickUpDelay(0);
            droppedItem.setDeltaMovement(0, 0.2F, 0);
            level.addFreshEntity(droppedItem);
        }
        onConversionSuccess(level, topBlockPos);
    }

    public static void onConversionSuccess(Level level, BlockPos pos) {
        for (int i = 0; i < 8; i++) {
            level.addParticle(
                    ParticleTypes.FLAME,
                    level.random.triangle(pos.getCenter().x, 0.5),
                    level.random.triangle(pos.getCenter().y, 0.2),
                    level.random.triangle(pos.getCenter().z, 0.5),
                    level.random.triangle(0, 0.1),
                    0.2,
                    level.random.triangle(0, 0.1)
            );
        }
        level.playSound(
                null,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                SoundEvents.FIRECHARGE_USE,
                SoundSource.BLOCKS,
                0.5f,
                0.5f
        );
    }

    public static void onConversionFail(Level level, BlockPos pos) {
        for (int i = 0; i < 8; i++) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    level.random.triangle(pos.getCenter().x, 0.5),
                    level.random.triangle(pos.getCenter().y, 0.2),
                    level.random.triangle(pos.getCenter().z, 0.5),
                    level.random.triangle(0, 0.1),
                    0.2,
                    level.random.triangle(0, 0.1)
            );
        }
        level.playSound(
                null,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                SoundEvents.FIRE_EXTINGUISH,
                SoundSource.BLOCKS,
                0.5f,
                0.5f
        );
    }

    public void updateStateFromBlockEntity(Level level, BlockPos blockPos, BlockState state) {
        if (!(level.getBlockEntity(blockPos) instanceof FlameHolderBlockEntity blockEntity)) {
            Firelight.LOGGER.warn("Ignoring state update attempt without matching block entity at {}: {}", blockPos, level.getBlockEntity(blockPos));
            return;
        }
        ItemStack flameStack = blockEntity.getTheItem();
        int flameLevel = flameStack.isEmpty() ? PyroStages.getMinLevel()
                                              : ((FlameItem) flameStack.getItem()).getPyroStage().getLevel();

        level.setBlockAndUpdate(
                blockPos,
                state.setValue(FlameHolderBlock.HOLDING_FLAME, !flameStack.isEmpty())
                     .setValue(FlameHolderBlock.FLAME_LEVEL, flameLevel)
        );
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(state));
    }

    @Override
    protected void neighborChanged(
            BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos,
            boolean movedByPiston
    ) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        boolean powered = level.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);

        updateStateFromBlockEntity(level, pos, state);
        if (!triggered && powered) {
            level.scheduleTick(pos, this, 4);
            level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, true));
        } else if (triggered && !powered) {
            level.setBlockAndUpdate(pos, state.setValue(TRIGGERED, false));
        }
    }

    @NotNull
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult
    ) {
        if (!(level.getBlockEntity(pos) instanceof FlameHolderBlockEntity blockEntity)) {
            Firelight.LOGGER.warn("Ignoring empty interaction attempt without matching block entity at {}: {}", pos, level.getBlockEntity(pos));
            return InteractionResult.FAIL;
        }

        if (!state.getValue(HOLDING_FLAME)) {
            return InteractionResult.FAIL;
        }

        ItemStack stack = blockEntity.getTheItem();

        blockEntity.clearContent();
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }

        updateStateFromBlockEntity(level, pos, state);
        level.setBlockAndUpdate(pos, state.setValue(HOLDING_FLAME, false));
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        return InteractionResult.SUCCESS;
    }

    private ItemInteractionResult convertToItemResult(InteractionResult result) {
        return switch (result) {
            case SUCCESS, SUCCESS_NO_ITEM_USED -> ItemInteractionResult.SUCCESS;
            case CONSUME -> ItemInteractionResult.CONSUME;
            case CONSUME_PARTIAL -> ItemInteractionResult.CONSUME_PARTIAL;
            case PASS -> ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            case FAIL -> ItemInteractionResult.FAIL;
        };
    }

    @NotNull
    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (!(stack.getItem() instanceof FlameItem)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (!(level.getBlockEntity(pos) instanceof FlameHolderBlockEntity blockEntity)) {
            Firelight.LOGGER.warn("Ignoring item interaction attempt without matching block entity at {}: {}", pos, level.getBlockEntity(pos));
            return ItemInteractionResult.FAIL;
        }

        if (state.getValue(FlameHolderBlock.HOLDING_FLAME)) {
            return convertToItemResult(useWithoutItem(state, level, pos, player, hitResult));
        }

        if (!blockEntity.canPlaceItem(0, stack)) {
            blockEntity.setTheItem(stack);
        } else {
            return ItemInteractionResult.FAIL;
        }

        updateStateFromBlockEntity(level, pos, state);
        player.setItemInHand(hand, ItemStack.EMPTY);
        return ItemInteractionResult.CONSUME;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (!state.hasProperty(FLAME_LEVEL) || !state.hasProperty(HOLDING_FLAME)) {
            return 0;
        }
        if (!state.getValue(HOLDING_FLAME)) return 0;

        return Mth.ceil(Mth.map(
                state.getValue(FLAME_LEVEL),
                PyroStages.getMinLevel(),
                PyroStages.getMaxLevel(),
                1, 15
        ));
    }

    public static int getLightingLevel(BlockState state) {
        if (!state.hasProperty(FLAME_LEVEL) || !state.hasProperty(HOLDING_FLAME)) {
            return 0;
        }
        if (!state.getValue(HOLDING_FLAME)) return 0;

        return Mth.ceil(Mth.map(
                state.getValue(FLAME_LEVEL),
                PyroStages.getMinLevel(),
                PyroStages.getMaxLevel(),
                1, 15
        ));
    }

    @NotNull
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @NotNull
    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}
