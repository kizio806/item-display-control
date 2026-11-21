package com.kizio.itemdisplaycontrol.common.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectionMathTest {

    @Test
    void shouldClampCoordinatesWithinBlockBounds() {
        assertEquals(10.5D, ProjectionMath.clampToBlock(10.5D, 10));
        assertEquals(10.0D, ProjectionMath.clampToBlock(10.0D, 10));
        assertEquals(11.0D, ProjectionMath.clampToBlock(11.0D, 10));
    }

    @Test
    void shouldClampCoordinatesBelowBlockBounds() {
        assertEquals(10.0D, ProjectionMath.clampToBlock(9.9D, 10));
        assertEquals(10.0D, ProjectionMath.clampToBlock(5.0D, 10));
    }

    @Test
    void shouldClampCoordinatesAboveBlockBounds() {
        assertEquals(11.0D, ProjectionMath.clampToBlock(11.1D, 10));
        assertEquals(11.0D, ProjectionMath.clampToBlock(15.0D, 10));
    }

    @Test
    void shouldHandleNegativeBlockCoordinates() {
        assertEquals(-5.5D, ProjectionMath.clampToBlock(-5.5D, -6));
        assertEquals(-6.0D, ProjectionMath.clampToBlock(-6.5D, -6));
        assertEquals(-5.0D, ProjectionMath.clampToBlock(-4.5D, -6));
    }

    @Test
    void shouldProjectAxisCorrectly() {
        assertEquals(10.0D, ProjectionMath.projectAxis(true, 10));
        assertEquals(11.0D, ProjectionMath.projectAxis(false, 10));

        assertEquals(-5.0D, ProjectionMath.projectAxis(true, -5));
        assertEquals(-4.0D, ProjectionMath.projectAxis(false, -5));
    }
}
