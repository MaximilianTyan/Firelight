package com.github.maximiliantyan.recipe.purification;

import com.github.maximiliantyan.core.FlameStage;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class PurificationRecipeSerializer implements RecipeSerializer<PurificationRecipe> {

    private final MapCodec<PurificationRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, PurificationRecipe> streamCodec;

    /**
     * {@link net.minecraft.world.item.crafting.ShapelessRecipe}
     */

    public PurificationRecipeSerializer() {
        this.codec = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                          Ingredient.CODEC_NONEMPTY.fieldOf("ingredient")
                                                                   .forGetter(PurificationRecipe::getIngredient),
                                          ItemStack.STRICT_CODEC.fieldOf("result")
                                                                .forGetter(PurificationRecipe::getResultItem),
                                          FlameStage.CODEC.fieldOf("stage")
                                                          .forGetter(PurificationRecipe::getRequiredFlameStage)
                                  )
                                  .apply(builder, PurificationRecipe::new)
        );

        this.streamCodec = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                PurificationRecipe::getIngredient,
                ItemStack.STREAM_CODEC,
                PurificationRecipe::getResultItem,
                FlameStage.STREAM_CODEC,
                PurificationRecipe::getRequiredFlameStage,
                PurificationRecipe::new
        );
    }

    @Override
    public MapCodec<PurificationRecipe> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PurificationRecipe> streamCodec() {
        return this.streamCodec;
    }
}