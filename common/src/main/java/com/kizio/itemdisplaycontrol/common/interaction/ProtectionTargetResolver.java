package com.kizio.itemdisplaycontrol.common.interaction;

import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;

import java.util.Objects;
public final class ProtectionTargetResolver {

    private static final String VANILLA_NAMESPACE = "minecraft";

    private ProtectionTargetResolver() {
    }

    public static ProtectionTarget resolveBlockTarget(String namespace, String path) {
        if (!isVanilla(namespace) || path == null) {
            return null;
        }

        return switch (path) {
            case "flower_pot" -> ProtectionTarget.FLOWER_POTS;
            case "decorated_pot" -> ProtectionTarget.DECORATED_POTS;
            case "chiseled_bookshelf" -> ProtectionTarget.CHISELED_BOOKSHELVES;
            case "lectern" -> ProtectionTarget.LECTERNS;
            case "jukebox" -> ProtectionTarget.JUKEBOXES;
            case "campfire", "soul_campfire" -> ProtectionTarget.CAMPFIRES;
            case "composter" -> ProtectionTarget.COMPOSTERS;
            case "respawn_anchor" -> ProtectionTarget.RESPAWN_ANCHORS;
            default -> null;
        };
    }

    public static ProtectionTarget resolveEntityTarget(String namespace, String path) {
        if (!isVanilla(namespace) || path == null) {
            return null;
        }

        return switch (path) {
            case "item_frame", "glow_item_frame" -> ProtectionTarget.ITEM_FRAMES;
            case "armor_stand" -> ProtectionTarget.ARMOR_STANDS;
            default -> null;
        };
    }

    private static boolean isVanilla(String namespace) {
        return Objects.equals(VANILLA_NAMESPACE, namespace);
    }
}
