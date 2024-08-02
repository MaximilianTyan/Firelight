package com.github.maximiliantyan.recipe.purification;

import com.github.maximiliantyan.core.PyroStage;
import com.github.maximiliantyan.utils.LookupProvider;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

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
                                                                   .forGetter(recipe -> recipe.getIngredients().getFirst()),
                                          ItemStack.STRICT_CODEC.fieldOf("result")
                                                                .forGetter(recipe -> recipe.getResultItem(LookupProvider.EMPTY)),
                                          PyroStage.CODEC.fieldOf("flame")
                                                         .forGetter(PurificationRecipe::getRequiredFlame)
                                  )
                                  .apply(builder, PurificationRecipe::new)
        );

        this.streamCodec = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                recipe -> recipe.getIngredients().getFirst(),
                ItemStack.STREAM_CODEC,
                recipe -> recipe.getResultItem(LookupProvider.EMPTY),
                PyroStage.STREAM_CODEC,
                PurificationRecipe::getRequiredFlame,
                PurificationRecipe::new
        );
    }

    @NotNull
    @Override
    public MapCodec<PurificationRecipe> codec() {
        return this.codec;
    }

    @NotNull
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PurificationRecipe> streamCodec() {
        return this.streamCodec;
    }
}