package com.kizio.itemdisplaycontrol.fabric.client.input;

import com.kizio.itemdisplaycontrol.common.Constants;
import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class FabricKeyMappings {

    private static final int DEFAULT_TOGGLE_KEY = GLFW.GLFW_KEY_J;
    private static final int DEFAULT_CONFIG_KEY = GLFW.GLFW_KEY_O;
    private static final KeyBinding.Category KEY_CATEGORY = KeyBinding.Category.create(
            Identifier.of(Constants.MOD_ID, Constants.MOD_ID)
    );
    private static KeyBinding toggleKey;
    private static KeyBinding configKey;

    private FabricKeyMappings() {
    }

    public static void register() {
        if (toggleKey != null) {
            return;
        }

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                TranslationKeys.KEY_TOGGLE,
                InputUtil.Type.KEYSYM,
                DEFAULT_TOGGLE_KEY,
                KEY_CATEGORY
        ));

        configKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                TranslationKeys.KEY_CONFIG,
                InputUtil.Type.KEYSYM,
                DEFAULT_CONFIG_KEY,
                KEY_CATEGORY
        ));
    }

    public static boolean consumeTogglePress() {
        if (toggleKey == null) {
            return false;
        }

        boolean pressed = false;
        while (toggleKey.wasPressed()) {
            pressed = true;
        }
        return pressed;
    }

    public static boolean consumeConfigScreenPress() {
        if (configKey == null) {
            return false;
        }

        boolean pressed = false;
        while (configKey.wasPressed()) {
            pressed = true;
        }
        return pressed;
    }
}
