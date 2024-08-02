package com.github.maximiliantyan.recipe.incineration;

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

public class IncinerationRecipeSerializer implements RecipeSerializer<IncinerationRecipe> {

    private final MapCodec<IncinerationRecipe> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, IncinerationRecipe> streamCodec;

    public IncinerationRecipeSerializer() {
        this.codec = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                          Ingredient.CODEC_NONEMPTY.fieldOf("ingredient")
                                                                   .forGetter(recipe -> recipe.getIngredients().getFirst()),
                                          ItemStack.STRICT_CODEC.fieldOf("result")
                                                                .forGetter(recipe -> recipe.getResultItem(LookupProvider.EMPTY)),
                                          PyroStage.CODEC.fieldOf("flame")
                                                         .forGetter(IncinerationRecipe::getRequiredFlame)
                                  )
                                  .apply(builder, IncinerationRecipe::new)
        );

        this.streamCodec = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                recipe -> recipe.getIngredients().getFirst(),
                ItemStack.STREAM_CODEC,
                recipe -> recipe.getResultItem(LookupProvider.EMPTY),
                PyroStage.STREAM_CODEC,
                IncinerationRecipe::getRequiredFlame,
                IncinerationRecipe::new
        );
    }

    @NotNull
    @Override
    public MapCodec<IncinerationRecipe> codec() {
        return this.codec;
    }

    @NotNull
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, IncinerationRecipe> streamCodec() {
        return this.streamCodec;
    }
}