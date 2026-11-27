package com.kizio.itemdisplaycontrol.common.config;

import java.util.Arrays;
import java.util.List;
public enum ProtectionTarget {
    ITEM_FRAMES("itemFrames", "message.itemdisplaycontrol.target.item_frames"),
    ARMOR_STANDS("armorStands", "message.itemdisplaycontrol.target.armor_stands"),
    FLOWER_POTS("flowerPots", "message.itemdisplaycontrol.target.flower_pots"),
    DECORATED_POTS("decoratedPots", "message.itemdisplaycontrol.target.decorated_pots"),
    CHISELED_BOOKSHELVES("chiseledBookshelves", "message.itemdisplaycontrol.target.chiseled_bookshelves"),
    LECTERNS("lecterns", "message.itemdisplaycontrol.target.lecterns"),
    JUKEBOXES("jukeboxes", "message.itemdisplaycontrol.target.jukeboxes"),
    CAMPFIRES("campfires", "message.itemdisplaycontrol.target.campfires"),
    COMPOSTERS("composters", "message.itemdisplaycontrol.target.composters"),
    RESPAWN_ANCHORS("respawnAnchors", "message.itemdisplaycontrol.target.respawn_anchors");

    private static final List<ProtectionTarget> ORDERED_VALUES = List.copyOf(Arrays.asList(values()));

    private final String propertyKey;
    private final String translationKey;

    ProtectionTarget(String propertyKey, String translationKey) {
        this.propertyKey = propertyKey;
        this.translationKey = translationKey;
    }

    public String propertyKey() {
        return propertyKey;
    }

    public String translationKey() {
        return translationKey;
    }

    public static List<ProtectionTarget> orderedValues() {
        return ORDERED_VALUES;
    }
}
