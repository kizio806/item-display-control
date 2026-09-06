package com.kizio.itemdisplaycontrol.common;

import com.kizio.itemdisplaycontrol.common.i18n.TranslationKeys;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ConstantsContractTest {

    @Test
    void shouldExposeStableConstantsAndTranslationKeys() {
        assertEquals("itemdisplaycontrol", Constants.MOD_ID);
        assertEquals("Item Display Control", Constants.MOD_NAME);

        assertEquals("category.itemdisplaycontrol", TranslationKeys.CATEGORY);
        assertEquals("key.itemdisplaycontrol.toggle", TranslationKeys.KEY_TOGGLE);
        assertEquals("key.itemdisplaycontrol.config", TranslationKeys.KEY_CONFIG);
        assertEquals("message.itemdisplaycontrol.status", TranslationKeys.MESSAGE_STATUS);
        assertEquals("message.itemdisplaycontrol.enabled", TranslationKeys.MESSAGE_ENABLED);
        assertEquals("message.itemdisplaycontrol.disabled", TranslationKeys.MESSAGE_DISABLED);
        assertEquals("screen.itemdisplaycontrol.settings.title", TranslationKeys.SCREEN_SETTINGS_TITLE);
        assertEquals("screen.itemdisplaycontrol.settings.subtitle", TranslationKeys.SCREEN_SETTINGS_SUBTITLE);
        assertEquals("gui.itemdisplaycontrol.enabled", TranslationKeys.GUI_ENABLED);
        assertEquals("gui.itemdisplaycontrol.whitelist.toggle", TranslationKeys.GUI_WHITELIST_TOGGLE);
        assertEquals("gui.itemdisplaycontrol.blacklist.toggle", TranslationKeys.GUI_BLACKLIST_TOGGLE);
        assertEquals("gui.itemdisplaycontrol.whitelist.items", TranslationKeys.GUI_WHITELIST_ITEMS);
        assertEquals("gui.itemdisplaycontrol.blacklist.items", TranslationKeys.GUI_BLACKLIST_ITEMS);
        assertEquals("gui.itemdisplaycontrol.option.done", TranslationKeys.GUI_OPTION_DONE);
        assertEquals("gui.itemdisplaycontrol.desc.primary", TranslationKeys.GUI_DESC_PRIMARY);
        assertEquals("gui.itemdisplaycontrol.desc.rules", TranslationKeys.GUI_DESC_RULES);
        assertEquals("gui.itemdisplaycontrol.desc.secondary", TranslationKeys.GUI_DESC_SECONDARY);
    }
}
