package com.kizio.itemdisplaycontrol.common.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ItemDisplayConfigStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void loadMissingFileReturnsDefaults() {
        ItemDisplayRuntimeConfig config = ItemDisplayConfigStorage.load(tempDir.resolve("missing.properties"));

        assertTrue(config.enabled());
        assertTrue(config.isProtected(ProtectionTarget.ITEM_FRAMES));
        assertTrue(config.isProtected(ProtectionTarget.FLOWER_POTS));
    }

    @Test
    void saveAndReloadPreservesState() {
        Path file = tempDir.resolve("itemdisplaycontrol.properties");
        ItemDisplayRuntimeConfig config = ItemDisplayRuntimeConfig.defaults();
        config.setEnabled(false);
        config.setWhitelistEnabled(true);
        config.setBlacklistEnabled(true);
        config.setProtected(ProtectionTarget.ITEM_FRAMES, false);
        config.setProtected(ProtectionTarget.CAMPFIRES, false);
        config.setWhitelistedItems(ItemDisplayRuntimeConfig.parseItemList("minecraft:diamond, emerald"));
        config.setBlacklistedItems(ItemDisplayRuntimeConfig.parseItemList("minecraft:tnt"));

        ItemDisplayConfigStorage.save(file, config);
        ItemDisplayRuntimeConfig reloaded = ItemDisplayConfigStorage.load(file);

        assertFalse(reloaded.enabled());
        assertTrue(reloaded.whitelistEnabled());
        assertTrue(reloaded.blacklistEnabled());
        assertFalse(reloaded.isProtected(ProtectionTarget.ITEM_FRAMES));
        assertFalse(reloaded.isProtected(ProtectionTarget.CAMPFIRES));
        assertTrue(reloaded.isProtected(ProtectionTarget.JUKEBOXES));
        assertTrue(reloaded.whitelistedItems().contains("minecraft:diamond"));
        assertTrue(reloaded.whitelistedItems().contains("minecraft:emerald"));
        assertTrue(reloaded.blacklistedItems().contains("minecraft:tnt"));
    }

    @Test
    void parseItemListNormalizesAndDeduplicates() {
        assertTrue(ItemDisplayRuntimeConfig.parseItemList("diamond, minecraft:diamond, bad item, mod:tool").contains("minecraft:diamond"));
        assertTrue(ItemDisplayRuntimeConfig.parseItemList("diamond, minecraft:diamond, bad item, mod:tool").contains("mod:tool"));
    }

    @Test
    void whitelistAndBlacklistRulesAreApplied() {
        ItemDisplayRuntimeConfig config = ItemDisplayRuntimeConfig.defaults();

        assertFalse(config.canInsert("minecraft:diamond"));

        config.setBlacklistEnabled(true);
        assertTrue(config.canInsert("minecraft:diamond"));

        config.setBlacklistedItems(ItemDisplayRuntimeConfig.parseItemList("minecraft:tnt"));
        assertFalse(config.canInsert("minecraft:tnt"));
        assertTrue(config.canInsert("minecraft:diamond"));

        config.setWhitelistEnabled(true);
        config.setWhitelistedItems(ItemDisplayRuntimeConfig.parseItemList("minecraft:diamond"));
        assertTrue(config.canInsert("minecraft:diamond"));
        assertFalse(config.canInsert("minecraft:emerald"));
    }
}
