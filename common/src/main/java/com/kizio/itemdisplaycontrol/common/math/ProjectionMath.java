package com.kizio.itemdisplaycontrol.common.math;

/**
 * Pure mathematical utilities for clamping raycast coordinates and calculating block boundaries.
 */
public final class ProjectionMath {

    private ProjectionMath() {
    }

    /**
     * Clamps a coordinate within the unit boundaries of a block coordinate.
     *
     * @param coordinate the continuous coordinate.
     * @param blockCoordinate the discrete block origin coordinate.
     * @return clamped coordinate in {@code [blockCoordinate, blockCoordinate + 1.0]}.
     */
    public static double clampToBlock(double coordinate, int blockCoordinate) {
        return Math.min(Math.max(coordinate, blockCoordinate), blockCoordinate + 1.0D);
    }

    /**
     * Projects a coordinate onto the near or far boundary of a block along one axis.
     *
     * @param isNegativeSide {@code true} for negative face (min bound), {@code false} for positive face (max bound).
     * @param blockCoordinate the discrete block origin.
     * @return plane coordinate boundary.
     */
    public static double projectAxis(boolean isNegativeSide, int blockCoordinate) {
        return isNegativeSide ? (double) blockCoordinate : (double) blockCoordinate + 1.0D;
    }
}
