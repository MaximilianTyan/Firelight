package com.github.maximiliantyan.generation.recipes;

import com.github.maximiliantyan.core.PyroStages;
import com.github.maximiliantyan.recipe.incineration.IncinerationRecipeBuilder;
import com.github.maximiliantyan.utils.IdUtils;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class IncinerationRecipeGenerator {

    public static String CATEGORY = "incineration";

    public static void buildRecipes(RecipeOutput exporter) {
        // Spark
        {
            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "water"),
                    Items.ICE,
                    Items.WATER_BUCKET,
                    PyroStages.SPARK
            );
        }

        // Flame
        {
            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "white_concrete"),
                    Ingredient.of(
                            Items.BROWN_CONCRETE,
                            Items.RED_CONCRETE,
                            Items.ORANGE_CONCRETE,
                            Items.YELLOW_CONCRETE,
                            Items.LIME_CONCRETE,
                            Items.GREEN_CONCRETE,
                            Items.LIGHT_BLUE_CONCRETE,
                            Items.CYAN_CONCRETE,
                            Items.BLUE_CONCRETE,
                            Items.PINK_CONCRETE,
                            Items.MAGENTA_CONCRETE,
                            Items.PURPLE_CONCRETE,
                            Items.WHITE_CONCRETE,
                            Items.LIGHT_GRAY_CONCRETE,
                            Items.GRAY_CONCRETE,
                            Items.BLACK_CONCRETE
                    ),
                    Items.WHITE_CONCRETE,
                    PyroStages.FLAME
            );

            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "glass"),
                    Ingredient.of(
                            Items.BROWN_STAINED_GLASS,
                            Items.RED_STAINED_GLASS,
                            Items.ORANGE_STAINED_GLASS,
                            Items.YELLOW_STAINED_GLASS,
                            Items.LIME_STAINED_GLASS,
                            Items.GREEN_STAINED_GLASS,
                            Items.LIGHT_BLUE_STAINED_GLASS,
                            Items.CYAN_STAINED_GLASS,
                            Items.BLUE_STAINED_GLASS,
                            Items.PINK_STAINED_GLASS,
                            Items.MAGENTA_STAINED_GLASS,
                            Items.PURPLE_STAINED_GLASS,
                            Items.WHITE_STAINED_GLASS,
                            Items.LIGHT_GRAY_STAINED_GLASS,
                            Items.GRAY_STAINED_GLASS,
                            Items.BLACK_STAINED_GLASS
                    ),
                    Items.GLASS,
                    PyroStages.FLAME
            );

            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "glass_pane"),
                    Ingredient.of(
                            Items.BROWN_STAINED_GLASS_PANE,
                            Items.RED_STAINED_GLASS_PANE,
                            Items.ORANGE_STAINED_GLASS_PANE,
                            Items.YELLOW_STAINED_GLASS_PANE,
                            Items.LIME_STAINED_GLASS_PANE,
                            Items.GREEN_STAINED_GLASS_PANE,
                            Items.LIGHT_BLUE_STAINED_GLASS_PANE,
                            Items.CYAN_STAINED_GLASS_PANE,
                            Items.BLUE_STAINED_GLASS_PANE,
                            Items.PINK_STAINED_GLASS_PANE,
                            Items.MAGENTA_STAINED_GLASS_PANE,
                            Items.PURPLE_STAINED_GLASS_PANE,
                            Items.WHITE_STAINED_GLASS_PANE,
                            Items.LIGHT_GRAY_STAINED_GLASS_PANE,
                            Items.GRAY_STAINED_GLASS_PANE,
                            Items.BLACK_STAINED_GLASS_PANE
                    ),
                    Items.GLASS_PANE,
                    PyroStages.FLAME
            );
        }

        // Fire
        {
            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "tinted_glass"),
                    Items.GLASS,
                    Items.TINTED_GLASS,
                    PyroStages.FIRE
            );
        }

        // Brasero
        {
            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "quartz_block"),
                    Items.COAL_BLOCK,
                    Items.QUARTZ_BLOCK,
                    PyroStages.BRASERO
            );
        }
        // Bonfire
        {
            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "diamond"),
                    Items.QUARTZ_BLOCK,
                    Items.DIAMOND,
                    PyroStages.BONFIRE
            );
        }

        // Inferno
        {
            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "lava"),
                    Items.MAGMA_BLOCK,
                    Items.LAVA_BUCKET,
                    PyroStages.INFERNO
            );

            IncinerationRecipeBuilder.incinerate(
                    exporter,
                    IdUtils.newRecipeId(CATEGORY, "ancient_debris"),
                    Items.IRON_BLOCK,
                    Items.ANCIENT_DEBRIS,
                    PyroStages.INFERNO
            );
        }
    }
}
