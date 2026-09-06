package com.kizio.itemdisplaycontrol.neoforge.client.input;

import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public final class NeoForgeKeyMappings {

    private static final int DEFAULT_TOGGLE_KEY = GLFW.GLFW_KEY_J;
    private static final int DEFAULT_CONFIG_KEY = GLFW.GLFW_KEY_O;

    @SuppressWarnings("deprecation")
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(
            net.minecraft.resources.Identifier.parse("itemdisplaycontrol:itemdisplaycontrol")
    );

    private static final KeyMapping TOGGLE_KEY = new KeyMapping(
            TranslationKeys.KEY_TOGGLE,
            DEFAULT_TOGGLE_KEY,
            KEY_CATEGORY
    );
    private static final KeyMapping CONFIG_KEY = new KeyMapping(
            TranslationKeys.KEY_CONFIG,
            DEFAULT_CONFIG_KEY,
            KEY_CATEGORY
    );

    private NeoForgeKeyMappings() {
    }

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
        event.register(CONFIG_KEY);
    }

    public static boolean consumeTogglePress() {
        boolean pressed = false;
        while (TOGGLE_KEY.consumeClick()) {
            pressed = true;
        }
        return pressed;
    }

    public static boolean consumeConfigScreenPress() {
        boolean pressed = false;
        while (CONFIG_KEY.consumeClick()) {
            pressed = true;
        }
        return pressed;
    }
}
