package com.kizio.itemdisplaycontrol.common.config;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ProtectionTargetContractTest {

    @Test
    void orderedValuesShouldExposeStableAndUnmodifiableOrder() {
        List<ProtectionTarget> orderedValues = ProtectionTarget.orderedValues();

        assertEquals(List.of(ProtectionTarget.values()), orderedValues);
        assertThrows(UnsupportedOperationException.class, () -> orderedValues.add(ProtectionTarget.ITEM_FRAMES));
    }

    @Test
    void eachTargetShouldExposePropertyAndTranslationKeys() {
        for (ProtectionTarget target : ProtectionTarget.orderedValues()) {
            assertFalse(target.propertyKey().isBlank());
            assertTrue(target.translationKey().startsWith("message.itemdisplaycontrol.target."));
        }
    }
}
