package com.github.maximiliantyan.generation.recipes;

import com.github.maximiliantyan.items.FlameItem;
import com.github.maximiliantyan.registry.ModItems;
import com.github.maximiliantyan.utils.IdUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

public class CraftingRecipeGenerator {

    private static final String CATEGORY = "crafting";

    public static void buildRecipes(RecipeOutput exporter) {
        buildUpgradeRecipes(exporter);
        buildMiscRecipes(exporter);
    }

    public static Ingredient flameOrAbove(Item flame) {
        return flameOrAbove((FlameItem) flame);
    }

    public static Ingredient flameOrAbove(FlameItem flame) {
        FlameItem[] allFlames = {
                (FlameItem) ModItems.SPARK,
                (FlameItem) ModItems.FLAME,
                (FlameItem) ModItems.FIRE,
                (FlameItem) ModItems.BRASERO,
                (FlameItem) ModItems.BONFIRE,
                (FlameItem) ModItems.INFERNO,
        };

        return Ingredient.of(
                Arrays.stream(allFlames)
                      .filter(item -> item.getPyroStage().getLevel() >= flame.getPyroStage().getLevel())
                      .map(ItemStack::new)
        );
    }

    private static void buildMiscRecipes(RecipeOutput exporter) {
        // Spark
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COAL)
                              .requires(flameOrAbove(ModItems.SPARK))
                              .requires(Items.CHARCOAL)
                              .unlockedBy("has_coals", FabricRecipeProvider.has(ItemTags.COALS))
                              .save(exporter, IdUtils.newRecipeId(CATEGORY, "coal"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ASH)
                              .requires(flameOrAbove(ModItems.SPARK))
                              .requires(Items.COAL)
                              .unlockedBy("has_coal", FabricRecipeProvider.has(Items.COAL))
                              .save(exporter, IdUtils.newRecipeId(CATEGORY, "ash"));

        // Flame
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLACK_DYE)
                              .requires(flameOrAbove(ModItems.FLAME))
                              .requires(Ingredient.of(
                                      Items.BROWN_DYE,
                                      Items.RED_DYE,
                                      Items.ORANGE_DYE,
                                      Items.YELLOW_DYE,
                                      Items.LIME_DYE,
                                      Items.GREEN_DYE,
                                      Items.LIGHT_BLUE_DYE,
                                      Items.BLUE_DYE,
                                      Items.PINK_DYE,
                                      Items.MAGENTA_DYE,
                                      Items.PURPLE_DYE,
                                      Items.WHITE_DYE,
                                      Items.LIGHT_GRAY_DYE,
                                      Items.GRAY_DYE
                              ))
                              .unlockedBy("has_glass", FabricRecipeProvider.has(Items.GLASS))
                              .save(exporter, IdUtils.newRecipeId(CATEGORY, "black_dye"));
    }

    private static void buildUpgradeRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SPARK)
                           .define('C', ItemTags.COALS)
                           .define('F', Items.CAMPFIRE)
                           .define('S', Items.STICK)
                           .define('G', Items.GUNPOWDER)
                           .pattern(" S ")
                           .pattern("SFS")
                           .pattern("CGC")
                           .unlockedBy("has_coal", FabricRecipeProvider.has(ItemTags.COALS))
                           .save(exporter, IdUtils.newRecipeId(CATEGORY, "spark"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FLAME)
                           .define('#', ModItems.SPARK)
                           .define('M', Items.PHANTOM_MEMBRANE)
                           .define('C', Items.COPPER_INGOT)
                           .define('A', Items.AMETHYST_SHARD)
                           .pattern("A#A")
                           .pattern("CMC")
                           .pattern(" C ")
                           .unlockedBy("has_spark", FabricRecipeProvider.has(ModItems.SPARK))
                           .save(exporter, IdUtils.newRecipeId(CATEGORY, "flame"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.FIRE)
                           .define('#', ModItems.FLAME)
                           .define('W', Items.WIND_CHARGE)
                           .define('F', Items.FIRE_CHARGE)
                           .define('I', Items.IRON_INGOT)
                           .pattern("W#W")
                           .pattern("IFI")
                           .pattern(" I ")
                           .unlockedBy("has_flame", FabricRecipeProvider.has(ModItems.FLAME))
                           .save(exporter, IdUtils.newRecipeId(CATEGORY, "fire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BRASERO)
                           .define('#', ModItems.FIRE)
                           .define('W', Items.WITHER_SKELETON_SKULL)
                           .define('S', Items.NETHERITE_SCRAP)
                           .define('B', Items.BLAZE_POWDER)
                           .pattern("B#B")
                           .pattern("SWS")
                           .pattern(" S ")
                           .unlockedBy("has_fire", FabricRecipeProvider.has(ModItems.FIRE))
                           .save(exporter, IdUtils.newRecipeId(CATEGORY, "brasero"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BONFIRE)
                           .define('#', ModItems.BRASERO)
                           .define('H', Items.HEART_OF_THE_SEA)
                           .define('E', Items.ECHO_SHARD)
                           .define('D', ItemTags.CREEPER_DROP_MUSIC_DISCS)
                           .pattern("H#H")
                           .pattern("EDE")
                           .pattern(" E ")
                           .unlockedBy("has_brasero", FabricRecipeProvider.has(ModItems.BRASERO))
                           .save(exporter, IdUtils.newRecipeId(CATEGORY, "bonfire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.INFERNO)
                           .define('#', ModItems.BONFIRE)
                           .define('S', Items.NETHER_STAR)
                           .define('N', Items.NETHERITE_INGOT)
                           .define('D', Items.DIAMOND)
                           .pattern("D#D")
                           .pattern("NSN")
                           .pattern(" N ")
                           .unlockedBy("has_bonfire", FabricRecipeProvider.has(ModItems.BONFIRE))
                           .save(exporter, IdUtils.newRecipeId(CATEGORY, "inferno"));


    }
}
