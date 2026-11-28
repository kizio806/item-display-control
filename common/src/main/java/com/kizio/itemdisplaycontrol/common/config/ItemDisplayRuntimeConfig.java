package com.kizio.itemdisplaycontrol.common.config;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
public final class ItemDisplayRuntimeConfig {

    private static final Pattern VALID_NAMESPACE = Pattern.compile("[a-z0-9_.-]+");
    private static final Pattern VALID_PATH = Pattern.compile("[a-z0-9_./-]+");

    private boolean enabled = true;
    private boolean whitelistEnabled;
    private boolean blacklistEnabled;
    private final EnumMap<ProtectionTarget, Boolean> protectedTargets = new EnumMap<>(ProtectionTarget.class);
    private final LinkedHashSet<String> whitelistedItems = new LinkedHashSet<>();
    private final LinkedHashSet<String> blacklistedItems = new LinkedHashSet<>();

    public ItemDisplayRuntimeConfig() {
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            protectedTargets.put(target, true);
        }
    }

    public boolean enabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean whitelistEnabled() {
        return whitelistEnabled;
    }

    public void setWhitelistEnabled(boolean whitelistEnabled) {
        this.whitelistEnabled = whitelistEnabled;
    }

    public boolean blacklistEnabled() {
        return blacklistEnabled;
    }

    public void setBlacklistEnabled(boolean blacklistEnabled) {
        this.blacklistEnabled = blacklistEnabled;
    }

    public boolean isProtected(ProtectionTarget target) {
        return protectedTargets.getOrDefault(target, true);
    }

    public void setProtected(ProtectionTarget target, boolean enabled) {
        protectedTargets.put(target, enabled);
    }

    public Map<ProtectionTarget, Boolean> protectedTargets() {
        return Map.copyOf(protectedTargets);
    }

    public Set<String> whitelistedItems() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(whitelistedItems));
    }

    public Set<String> blacklistedItems() {
        return Collections.unmodifiableSet(new LinkedHashSet<>(blacklistedItems));
    }

    public void setWhitelistedItems(Iterable<String> itemIds) {
        replaceItems(whitelistedItems, itemIds);
    }

    public void setBlacklistedItems(Iterable<String> itemIds) {
        replaceItems(blacklistedItems, itemIds);
    }

    public boolean canInsert(String itemId) {
        String normalizedItemId = normalizeItemId(itemId);
        if (normalizedItemId == null) {
            return false;
        }

        if (blacklistEnabled && blacklistedItems.contains(normalizedItemId)) {
            return false;
        }
        if (whitelistEnabled) {
            return whitelistedItems.contains(normalizedItemId);
        }
        if (blacklistEnabled) {
            return true;
        }
        return false;
    }
    public ItemDisplayRuntimeConfig copy() {
        ItemDisplayRuntimeConfig copy = new ItemDisplayRuntimeConfig();
        copy.enabled = enabled;
        copy.whitelistEnabled = whitelistEnabled;
        copy.blacklistEnabled = blacklistEnabled;
        protectedTargets.forEach(copy.protectedTargets::put);
        copy.whitelistedItems.addAll(whitelistedItems);
        copy.blacklistedItems.addAll(blacklistedItems);
        return copy;
    }

    public static List<String> parseItemList(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            return List.of();
        }

        LinkedHashSet<String> normalizedItems = new LinkedHashSet<>();
        String[] parts = rawValue.split("[\\s,;]+");
        for (String part : parts) {
            String normalized = normalizeItemId(part);
            if (normalized != null) {
                normalizedItems.add(normalized);
            }
        }

        return List.copyOf(normalizedItems);
    }

    public static String formatItemList(Iterable<String> itemIds) {
        StringBuilder builder = new StringBuilder();
        for (String itemId : itemIds) {
            String normalized = normalizeItemId(itemId);
            if (normalized == null) {
                continue;
            }
            if (!builder.isEmpty()) {
                builder.append(", ");
            }
            builder.append(normalized);
        }
        return builder.toString();
    }

    public static String normalizeItemId(String rawValue) {
        if (rawValue == null) {
            return null;
        }

        String value = rawValue.trim().toLowerCase(Locale.ROOT);
        if (value.isEmpty()) {
            return null;
        }

        String namespace;
        String path;
        int separatorIndex = value.indexOf(':');
        if (separatorIndex >= 0) {
            namespace = value.substring(0, separatorIndex);
            path = value.substring(separatorIndex + 1);
        } else {
            namespace = "minecraft";
            path = value;
        }

        if (!VALID_NAMESPACE.matcher(namespace).matches() || !VALID_PATH.matcher(path).matches()) {
            return null;
        }

        return namespace + ":" + path;
    }
    public static ItemDisplayRuntimeConfig defaults() {
        return new ItemDisplayRuntimeConfig();
    }

    private static void replaceItems(LinkedHashSet<String> target, Iterable<String> itemIds) {
        target.clear();
        for (String itemId : itemIds) {
            String normalized = normalizeItemId(itemId);
            if (normalized != null) {
                target.add(normalized);
            }
        }
    }
}
