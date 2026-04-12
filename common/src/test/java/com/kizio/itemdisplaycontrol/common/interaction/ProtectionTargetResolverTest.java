package com.kizio.itemdisplaycontrol.common.interaction;

import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

final class ProtectionTargetResolverTest {

    @Test
    void resolvesVanillaBlockTargets() {
        assertEquals(ProtectionTarget.FLOWER_POTS, ProtectionTargetResolver.resolveBlockTarget("minecraft", "flower_pot"));
        assertEquals(ProtectionTarget.DECORATED_POTS, ProtectionTargetResolver.resolveBlockTarget("minecraft", "decorated_pot"));
        assertEquals(ProtectionTarget.CHISELED_BOOKSHELVES, ProtectionTargetResolver.resolveBlockTarget("minecraft", "chiseled_bookshelf"));
        assertEquals(ProtectionTarget.LECTERNS, ProtectionTargetResolver.resolveBlockTarget("minecraft", "lectern"));
        assertEquals(ProtectionTarget.JUKEBOXES, ProtectionTargetResolver.resolveBlockTarget("minecraft", "jukebox"));
        assertEquals(ProtectionTarget.CAMPFIRES, ProtectionTargetResolver.resolveBlockTarget("minecraft", "campfire"));
        assertEquals(ProtectionTarget.CAMPFIRES, ProtectionTargetResolver.resolveBlockTarget("minecraft", "soul_campfire"));
        assertEquals(ProtectionTarget.COMPOSTERS, ProtectionTargetResolver.resolveBlockTarget("minecraft", "composter"));
        assertEquals(ProtectionTarget.RESPAWN_ANCHORS, ProtectionTargetResolver.resolveBlockTarget("minecraft", "respawn_anchor"));
    }

    @Test
    void resolvesVanillaEntityTargets() {
        assertEquals(ProtectionTarget.ITEM_FRAMES, ProtectionTargetResolver.resolveEntityTarget("minecraft", "item_frame"));
        assertEquals(ProtectionTarget.ITEM_FRAMES, ProtectionTargetResolver.resolveEntityTarget("minecraft", "glow_item_frame"));
        assertEquals(ProtectionTarget.ARMOR_STANDS, ProtectionTargetResolver.resolveEntityTarget("minecraft", "armor_stand"));
    }

    @Test
    void ignoresUnknownOrNonVanillaTargets() {
        assertNull(ProtectionTargetResolver.resolveBlockTarget("minecraft", "barrel"));
        assertNull(ProtectionTargetResolver.resolveBlockTarget("modded", "chiseled_bookshelf"));
        assertNull(ProtectionTargetResolver.resolveBlockTarget("modded", "oak_bookshelf"));
        assertNull(ProtectionTargetResolver.resolveEntityTarget("modded", "armor_stand"));
        assertNull(ProtectionTargetResolver.resolveEntityTarget("minecraft", "villager"));
        assertNull(ProtectionTargetResolver.resolveBlockTarget("minecraft", null));
        assertNull(ProtectionTargetResolver.resolveEntityTarget(null, "item_frame"));
    }
}
