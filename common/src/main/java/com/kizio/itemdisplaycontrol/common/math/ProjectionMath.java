package com.kizio.itemdisplaycontrol.common.math;

public final class ProjectionMath {

    private ProjectionMath() {
    }

    public static double clampToBlock(double coordinate, int blockCoordinate) {
        return Math.min(Math.max(coordinate, blockCoordinate), blockCoordinate + 1.0D);
    }

    public static double projectAxis(boolean isNegativeSide, int blockCoordinate) {
        return isNegativeSide ? (double) blockCoordinate : (double) blockCoordinate + 1.0D;
    }
}
