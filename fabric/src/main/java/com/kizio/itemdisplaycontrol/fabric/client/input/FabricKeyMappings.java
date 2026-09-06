package com.kizio.itemdisplaycontrol.fabric.client.input;

import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class FabricKeyMappings {

    private static final int DEFAULT_TOGGLE_KEY = GLFW.GLFW_KEY_J;
    private static final int DEFAULT_CONFIG_KEY = GLFW.GLFW_KEY_O;

    @SuppressWarnings("deprecation")
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(Constants.MOD_ID, "itemdisplaycontrol")
    );

    private static KeyMapping toggleKey;
    private static KeyMapping configKey;

    private FabricKeyMappings() {
    }

    public static void register() {
        if (toggleKey != null) {
            return;
        }

        toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                TranslationKeys.KEY_TOGGLE,
                InputConstants.Type.KEYSYM,
                DEFAULT_TOGGLE_KEY,
                KEY_CATEGORY
        ));

        configKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                TranslationKeys.KEY_CONFIG,
                InputConstants.Type.KEYSYM,
                DEFAULT_CONFIG_KEY,
                KEY_CATEGORY
        ));
    }

    public static boolean consumeTogglePress() {
        if (toggleKey == null) {
            return false;
        }

        boolean pressed = false;
        while (toggleKey.consumeClick()) {
            pressed = true;
        }
        return pressed;
    }

    public static boolean consumeConfigScreenPress() {
        if (configKey == null) {
            return false;
        }

        boolean pressed = false;
        while (configKey.consumeClick()) {
            pressed = true;
        }
        return pressed;
    }
}
