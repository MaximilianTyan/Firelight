package com.github.maximiliantyan.recipe.incineration;

import com.github.maximiliantyan.core.FlameStage;
import com.github.maximiliantyan.recipe.purification.PurificationRecipe;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class IncinerationRecipeSerializer implements RecipeSerializer<IncinerationRecipe> {

    private final MapCodec<IncinerationRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, IncinerationRecipe> streamCodec;

    /**
     * {@link net.minecraft.world.item.crafting.ShapelessRecipe}
     */

    public IncinerationRecipeSerializer() {
        this.codec = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                          Ingredient.CODEC_NONEMPTY.fieldOf("ingredient")
                                                                   .forGetter(IncinerationRecipe::getIngredient),
                                          ItemStack.STRICT_CODEC.fieldOf("result")
                                                                .forGetter(IncinerationRecipe::getResultItem),
                                          FlameStage.CODEC.fieldOf("stage")
                                                          .forGetter(IncinerationRecipe::getRequiredFlameStage)
                                  )
                                  .apply(builder, IncinerationRecipe::new)
        );

        this.streamCodec = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                IncinerationRecipe::getIngredient,
                ItemStack.STREAM_CODEC,
                IncinerationRecipe::getResultItem,
                FlameStage.STREAM_CODEC,
                IncinerationRecipe::getRequiredFlameStage,
                IncinerationRecipe::new
        );
    }

    @Override
    public MapCodec<IncinerationRecipe> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, IncinerationRecipe> streamCodec() {
        return this.streamCodec;
    }
}