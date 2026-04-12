package com.kizio.itemdisplaycontrol.common;

import com.kizio.itemdisplaycontrol.common.api.ToggleFeedback;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class ItemDisplayControlTest {

    @TempDir
    Path tempDir;

    @Test
    @Order(1)
    void onClientTickBeforeInitializationShouldBeNoOp() {
        ItemDisplayControl.onClientTick();
        ItemDisplayControl.flushPendingConfiguration();
    }

    @Test
    @Order(2)
    void togglesAndRulesShouldUpdateRuntimeState() {
        AtomicInteger toggleCount = new AtomicInteger();
        AtomicReference<Boolean> lastToggleValue = new AtomicReference<>();
        ItemDisplayControl.init(enabled -> {
            toggleCount.incrementAndGet();
            lastToggleValue.set(enabled);
        }, tempDir.resolve("runtime"));

        assertTrue(ItemDisplayControl.isEnabled());
        assertFalse(ItemDisplayControl.toggleEnabled());
        assertFalse(ItemDisplayControl.isEnabled());
        assertEquals(1, toggleCount.get());
        assertEquals(Boolean.FALSE, lastToggleValue.get());

        assertTrue(ItemDisplayControl.toggleEnabled());
        assertTrue(ItemDisplayControl.isEnabled());
        assertEquals(2, toggleCount.get());
        assertEquals(Boolean.TRUE, lastToggleValue.get());

        assertTrue(ItemDisplayControl.toggleWhitelistEnabled());
        ItemDisplayControl.setWhitelistItemsFromText("diamond,mod:tool,bad$item");
        assertEquals("minecraft:diamond, mod:tool", ItemDisplayControl.getWhitelistItemsText());

        assertFalse(ItemDisplayControl.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:diamond"));
        assertTrue(ItemDisplayControl.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:stick"));

        assertFalse(ItemDisplayControl.toggleProtection(ProtectionTarget.ITEM_FRAMES));
        assertFalse(ItemDisplayControl.isProtectionEnabled(ProtectionTarget.ITEM_FRAMES));
        assertFalse(ItemDisplayControl.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:stick"));

        ItemDisplayControl.setProtection(ProtectionTarget.ITEM_FRAMES, true);
        assertTrue(ItemDisplayControl.isProtectionEnabled(ProtectionTarget.ITEM_FRAMES));

        assertTrue(ItemDisplayControl.toggleBlacklistEnabled());
        assertTrue(ItemDisplayControl.isBlacklistEnabled());
        assertFalse(ItemDisplayControl.toggleWhitelistEnabled());
        assertFalse(ItemDisplayControl.isWhitelistEnabled());

        ItemDisplayControl.setBlacklistItemsFromText("minecraft:stick");
        assertTrue(ItemDisplayControl.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:stick"));
        assertFalse(ItemDisplayControl.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:diamond"));
    }

    @Test
    @Order(3)
    void flushShouldPersistConfiguration() throws IOException {
        Path configDirectory = tempDir.resolve("persist");
        ItemDisplayControl.init(ToggleFeedback.NO_OP, configDirectory);

        assertFalse(ItemDisplayControl.toggleEnabled());
        for (int index = 0; index < 12; index++) {
            ItemDisplayControl.onClientTick();
        }
        ItemDisplayControl.flushPendingConfiguration();

        Path configFile = configDirectory.resolve("itemdisplaycontrol.properties");
        assertTrue(Files.exists(configFile));

        String content = Files.readString(configFile, StandardCharsets.UTF_8);
        assertTrue(content.contains("enabled=false"));
        assertTrue(content.contains("protect.itemFrames=true"));
    }
}
