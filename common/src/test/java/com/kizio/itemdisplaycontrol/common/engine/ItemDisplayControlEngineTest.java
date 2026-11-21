package com.kizio.itemdisplaycontrol.common.engine;

import com.kizio.itemdisplaycontrol.common.api.ToggleFeedback;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDisplayControlEngineTest {

    @Test
    void shouldMaintainCompletelyIsolatedStateBetweenEngines(@TempDir Path tempDir) {
        Path dirA = tempDir.resolve("engineA");
        Path dirB = tempDir.resolve("engineB");

        ItemDisplayControlEngine engineA = new ItemDisplayControlEngine();
        ItemDisplayControlEngine engineB = new ItemDisplayControlEngine();

        engineA.init(ToggleFeedback.NO_OP, dirA);
        engineB.init(ToggleFeedback.NO_OP, dirB);

        assertTrue(engineA.isEnabled());
        assertTrue(engineB.isEnabled());

        engineA.toggleEnabled();
        assertFalse(engineA.isEnabled(), "Engine A must be toggled off");
        assertTrue(engineB.isEnabled(), "Engine B must remain on");

        engineA.setProtection(ProtectionTarget.ITEM_FRAMES, false);
        assertFalse(engineA.isProtectionEnabled(ProtectionTarget.ITEM_FRAMES));
        assertTrue(engineB.isProtectionEnabled(ProtectionTarget.ITEM_FRAMES));

        engineA.shutdown();
        engineB.shutdown();
    }

    @Test
    void shouldEvaluateBlockingAccurately(@TempDir Path tempDir) {
        ItemDisplayControlEngine engine = new ItemDisplayControlEngine();
        engine.init(ToggleFeedback.NO_OP, tempDir);

        assertTrue(engine.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:diamond"));

        engine.toggleWhitelistEnabled();
        engine.setWhitelistItemsFromText("minecraft:diamond, minecraft:emerald");
        assertFalse(engine.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:diamond"));
        assertTrue(engine.shouldBlock(ProtectionTarget.ITEM_FRAMES, "minecraft:iron_ingot"));

        engine.shutdown();
    }

    @Test
    void shouldToggleAndTrackProtectionTargets(@TempDir Path tempDir) {
        ItemDisplayControlEngine engine = new ItemDisplayControlEngine();
        engine.init(ToggleFeedback.NO_OP, tempDir);

        assertTrue(engine.isProtectionEnabled(ProtectionTarget.LECTERNS));
        boolean result = engine.toggleProtection(ProtectionTarget.LECTERNS);
        assertFalse(result);
        assertFalse(engine.isProtectionEnabled(ProtectionTarget.LECTERNS));

        engine.setProtection(ProtectionTarget.LECTERNS, true);
        assertTrue(engine.isProtectionEnabled(ProtectionTarget.LECTERNS));

        engine.shutdown();
    }

    @Test
    void shouldDebounceAndFlushConfiguration(@TempDir Path tempDir) {
        ItemDisplayControlEngine engine = new ItemDisplayControlEngine();
        engine.init(ToggleFeedback.NO_OP, tempDir);

        engine.toggleEnabled();
        for (int i = 0; i < ItemDisplayControlEngine.CONFIG_SAVE_DEBOUNCE_TICKS + 2; i++) {
            engine.onClientTick();
        }

        engine.flushPendingConfiguration();
        assertEquals(engine.isEnabled(), engine.getConfigSnapshot().enabled());

        engine.shutdown();
    }
}
