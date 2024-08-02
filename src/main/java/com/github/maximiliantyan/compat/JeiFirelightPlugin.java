package com.github.maximiliantyan.compat;

import com.github.maximiliantyan.Firelight;
import com.github.maximiliantyan.utils.IdUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


@JeiPlugin
public class JeiFirelightPlugin implements IModPlugin {

    public JeiFirelightPlugin() {}

    /**
     * The unique ID for this mod plugin.
     * The namespace should be your mod's modId.
     */
    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return IdUtils.newId("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        Firelight.LOGGER.info("Registering JEI categories");
        registration.addRecipeCategories(new JeiIncinerationRecipe(registration));
    }
}
