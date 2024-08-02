package com.github.maximiliantyan.entities;

import com.github.maximiliantyan.blocks.FlameHolderBlock;
import com.github.maximiliantyan.items.FlameItem;
import com.github.maximiliantyan.registry.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.ContainerSingleItem;
import org.jetbrains.annotations.NotNull;

public class FlameHolderBlockEntity extends BlockEntity implements Clearable, ContainerSingleItem.BlockContainerSingleItem {
    private @NotNull ItemStack flameStack = ItemStack.EMPTY;
    private final String CompoundTagKey = "FlameStack";

    public FlameHolderBlockEntity(
            BlockPos pos, BlockState blockState
    ) {
        super(ModBlockEntityTypes.FLAME_HOLDER, pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.getTheItem().isEmpty()) {
            tag.put(CompoundTagKey, this.getTheItem().save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains(CompoundTagKey, CompoundTag.TAG_COMPOUND)) {
            this.flameStack = ItemStack.parse(registries, tag.getCompound(CompoundTagKey))
                                       .orElse(ItemStack.EMPTY);
        } else {
            this.flameStack = ItemStack.EMPTY;
        }
    }

    private void updateBlockState() {
        FlameHolderBlock block = (FlameHolderBlock) this.getBlockState().getBlock();
        if (this.getLevel() == null) return;
        block.updateStateFromBlockEntity(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    @NotNull
    @Override
    public ItemStack getTheItem() {
        return this.flameStack;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return !flameStack.isEmpty() && !(flameStack.getItem() instanceof FlameItem);
    }

    @Override
    public void setTheItem(ItemStack item) {
        this.flameStack = flameStack.copy();
    }

    @NotNull
    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = BlockContainerSingleItem.super.removeItem(slot, amount);
        updateBlockState();
        return stack;
    }

    @NotNull
    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
