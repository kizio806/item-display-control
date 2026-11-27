package com.kizio.itemdisplaycontrol.common.config;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDisplayRuntimeConfigTest {

    @Test
    void shouldProvideExpectedDefaultConfiguration() {
        ItemDisplayRuntimeConfig config = ItemDisplayRuntimeConfig.defaults();

        assertTrue(config.enabled());
        assertFalse(config.whitelistEnabled());
        assertFalse(config.blacklistEnabled());
        assertTrue(config.whitelistedItems().isEmpty());
        assertTrue(config.blacklistedItems().isEmpty());

        for (ProtectionTarget target : ProtectionTarget.values()) {
            assertTrue(config.isProtected(target));
        }

        Map<ProtectionTarget, Boolean> targetsMap = config.protectedTargets();
        assertEquals(ProtectionTarget.values().length, targetsMap.size());
        for (ProtectionTarget target : ProtectionTarget.values()) {
            assertTrue(targetsMap.get(target));
        }
    }

    @Test
    void shouldCorrectlyEvaluateInsertionEligibilityUnderDifferentModes() {
        ItemDisplayRuntimeConfig config = ItemDisplayRuntimeConfig.defaults();

        // 1. Neither whitelist nor blacklist: everything blocked (canInsert = false)
        assertFalse(config.canInsert("minecraft:diamond"));
        assertFalse(config.canInsert("diamond"));
        assertFalse(config.canInsert(null));
        assertFalse(config.canInsert("invalid item ID!"));

        // 2. Whitelist enabled: only whitelisted items allowed
        config.setWhitelistEnabled(true);
        config.setWhitelistedItems(List.of("minecraft:diamond", "emerald"));

        assertTrue(config.canInsert("minecraft:diamond"));
        assertTrue(config.canInsert("minecraft:emerald"));
        assertTrue(config.canInsert("emerald"));
        assertFalse(config.canInsert("minecraft:iron_ingot"));
        assertFalse(config.canInsert(null));

        // 3. Blacklist enabled: blacklisted items blocked, others allowed
        config.setWhitelistEnabled(false);
        config.setBlacklistEnabled(true);
        config.setBlacklistedItems(List.of("minecraft:tnt", "lava_bucket"));

        assertFalse(config.canInsert("minecraft:tnt"));
        assertFalse(config.canInsert("lava_bucket"));
        assertTrue(config.canInsert("minecraft:diamond"));
        assertTrue(config.canInsert("minecraft:stick"));
        assertFalse(config.canInsert(null));

        // 4. Both enabled: blacklist takes precedence, then whitelist
        config.setWhitelistEnabled(true);
        config.setWhitelistedItems(List.of("minecraft:diamond", "minecraft:tnt"));
        config.setBlacklistedItems(List.of("minecraft:tnt"));

        assertTrue(config.canInsert("minecraft:diamond"));
        assertFalse(config.canInsert("minecraft:tnt"));
        assertFalse(config.canInsert("minecraft:iron_ingot"));
    }

    @Test
    void shouldParseItemListsWithVariousDelimitersAndNormalizations() {
        List<String> empty1 = ItemDisplayRuntimeConfig.parseItemList(null);
        List<String> empty2 = ItemDisplayRuntimeConfig.parseItemList("");
        List<String> empty3 = ItemDisplayRuntimeConfig.parseItemList("   \t\n  ");

        assertTrue(empty1.isEmpty());
        assertTrue(empty2.isEmpty());
        assertTrue(empty3.isEmpty());

        List<String> parsed = ItemDisplayRuntimeConfig.parseItemList(
                " diamond, minecraft:emerald; iron_ingot \n GOLD_INGOT, invalid$$item "
        );

        assertEquals(List.of(
                "minecraft:diamond",
                "minecraft:emerald",
                "minecraft:iron_ingot",
                "minecraft:gold_ingot"
        ), parsed);
    }

    @Test
    void shouldFormatItemListCorrectly() {
        assertEquals("", ItemDisplayRuntimeConfig.formatItemList(List.of()));

        String formatted = ItemDisplayRuntimeConfig.formatItemList(
                List.of("diamond", "minecraft:emerald", "invalid$$item", "gold_ingot")
        );
        assertEquals("minecraft:diamond, minecraft:emerald, minecraft:gold_ingot", formatted);
    }

    @Test
    void shouldNormalizeItemIdsAccurately() {
        assertNull(ItemDisplayRuntimeConfig.normalizeItemId(null));
        assertNull(ItemDisplayRuntimeConfig.normalizeItemId(""));
        assertNull(ItemDisplayRuntimeConfig.normalizeItemId("   "));
        assertNull(ItemDisplayRuntimeConfig.normalizeItemId("invalid namespace:item"));
        assertNull(ItemDisplayRuntimeConfig.normalizeItemId("namespace:invalid path!"));

        assertEquals("minecraft:stick", ItemDisplayRuntimeConfig.normalizeItemId("stick"));
        assertEquals("minecraft:stick", ItemDisplayRuntimeConfig.normalizeItemId("  STICK  "));
        assertEquals("mod_id:custom_item", ItemDisplayRuntimeConfig.normalizeItemId("mod_id:custom_item"));
        assertEquals("minecraft:item-with-dash", ItemDisplayRuntimeConfig.normalizeItemId("item-with-dash"));
    }

    @Test
    void shouldDeepCopyConfigurationState() {
        ItemDisplayRuntimeConfig original = ItemDisplayRuntimeConfig.defaults();
        original.setEnabled(false);
        original.setWhitelistEnabled(true);
        original.setBlacklistEnabled(true);
        original.setProtected(ProtectionTarget.ITEM_FRAMES, false);
        original.setWhitelistedItems(Set.of("minecraft:apple"));
        original.setBlacklistedItems(Set.of("minecraft:poisonous_potato"));

        ItemDisplayRuntimeConfig copy = original.copy();
        assertNotSame(original, copy);

        assertEquals(original.enabled(), copy.enabled());
        assertEquals(original.whitelistEnabled(), copy.whitelistEnabled());
        assertEquals(original.blacklistEnabled(), copy.blacklistEnabled());
        assertEquals(original.isProtected(ProtectionTarget.ITEM_FRAMES), copy.isProtected(ProtectionTarget.ITEM_FRAMES));
        assertEquals(original.isProtected(ProtectionTarget.ARMOR_STANDS), copy.isProtected(ProtectionTarget.ARMOR_STANDS));
        assertEquals(original.whitelistedItems(), copy.whitelistedItems());
        assertEquals(original.blacklistedItems(), copy.blacklistedItems());

        copy.setEnabled(true);
        copy.setProtected(ProtectionTarget.ITEM_FRAMES, true);
        copy.setWhitelistedItems(Set.of("minecraft:bread"));

        assertFalse(original.enabled());
        assertFalse(original.isProtected(ProtectionTarget.ITEM_FRAMES));
        assertEquals(Set.of("minecraft:apple"), original.whitelistedItems());
    }
}
