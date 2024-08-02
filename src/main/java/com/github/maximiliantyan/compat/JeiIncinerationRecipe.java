package com.github.maximiliantyan.compat;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.items.FlameItem;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipe;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipeBuilder;
import com.github.maximiliantyan.registry.ModItemTags;
import com.github.maximiliantyan.utils.LookupProvider;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JeiIncinerationRecipe implements IRecipeCategory<IncinerationRecipe> {
    IJeiHelpers jeiHelpers;

    public JeiIncinerationRecipe(IRecipeCategoryRegistration registration) {
        jeiHelpers = registration.getJeiHelpers();
    }

    /**
     * @return the type of recipe that this category handles.
     * @since 9.5.0
     */
    @Override
    public @NotNull RecipeType<IncinerationRecipe> getRecipeType() {
        return RecipeType.create(
                Firelight.MOD_ID,
                IncinerationRecipeBuilder.FOLDER,
                IncinerationRecipe.class
        );
    }

    /**
     * Returns a text component representing the name of this recipe type.
     * Drawn at the top of the recipe GUI pages for this category.
     *
     * @since 7.6.4
     */
    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(IncinerationRecipe.getTranslationKey());
    }

    /**
     * Returns the drawable background for a single recipe in this category.
     */
    @Override
    public @NotNull IDrawable getBackground() {
        IGuiHelper helper = jeiHelpers.getGuiHelper();
        return helper.createBlankDrawable(100, 100);
    }

    /**
     * Icon for the category tab.
     * You can use {@link IGuiHelper#createDrawableIngredient(IIngredientType, Object)}
     * to create a drawable from an ingredient.
     * <p>
     * If null is returned here, JEI will try to use the first recipe catalyst as the icon.
     *
     * @return icon to draw on the category tab, max size is 16x16 pixels.
     */
    @Override
    public @Nullable IDrawable getIcon() {
        IGuiHelper helper = jeiHelpers.getGuiHelper();
        IIngredientManager manager = jeiHelpers.getIngredientManager();
        Optional<ITypedIngredient<Ingredient>> ingredient =
                manager.createTypedIngredient(Ingredient.of(ModItemTags.FLAMES));
        if (ingredient.isPresent()) {
            return helper.createDrawableIngredient(ingredient.get());
        }
        Firelight.LOGGER.error("Failed to create typed ingredient from {}", Ingredient.of(ModItemTags.FLAMES));
        return null;
    }

    /**
     * Sets all the recipe's ingredients by filling out an instance of {@link IRecipeLayoutBuilder}.
     * This is used by JEI for lookups, to figure out what ingredients are inputs and outputs for a recipe.
     *
     * @param builder
     * @param recipe
     * @param focuses
     * @since 9.4.0
     */
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncinerationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 10)
               .setSlotName("Ingredient")
               .addIngredients(recipe.getIngredients().getFirst());

        Item flame = (Item) BuiltInRegistries.ITEM.stream()
                                                  .filter(item -> item instanceof FlameItem flameItem
                                                          && flameItem.getPyroStage() == recipe.getRequiredFlame());
        builder.addSlot(RecipeIngredientRole.CATALYST, 100, 10)
               .setSlotName("Flame")
               .addIngredients(Ingredient.of(flame));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 50, 30)
               .setSlotName("Product")
               .addItemStack(recipe.getResultItem(LookupProvider.EMPTY));
    }

}
