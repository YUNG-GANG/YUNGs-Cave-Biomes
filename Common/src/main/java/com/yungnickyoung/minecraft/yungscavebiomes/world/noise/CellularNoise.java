package com.yungnickyoung.minecraft.yungscavebiomes.world.noise;

import net.minecraft.util.Mth;

public class CellularNoise {

    private static final long PRIME_X = 0x5205402B9270C86FL;
    private static final long PRIME_Y = 0x598CD327003817B5L;
    private static final long PRIME_Z = 0x5BCC226E9FA0BACBL;
    private static final long HASH_MULTIPLIER = 0x53A3F72DEEC546F5L;

    private static final int N_JITTER_HASH_BITS_PER_AXIS = 21;
    private static final int JITTER_HASH_MASK = ((1 << N_JITTER_HASH_BITS_PER_AXIS) - 1);
    private static final float JITTER_HASH_RESCALE = 1.0f / (1 << N_JITTER_HASH_BITS_PER_AXIS);

    private static final double ROOT3OVER3 = 0.577350269189626;
    private static final double ROTATE3_ORTHOGONALIZER = -0.21132486540518713;

    public static final float MAX_DISTANCE_SQUARED_TO_CLOSEST = 3;
    public static final float MAX_DISTANCE_TO_CLOSEST = Mth.sqrt(MAX_DISTANCE_SQUARED_TO_CLOSEST);

    public static float sampleDistance2Sub_ImproveXZ(long seed, double x, double y, double z) {

        // Re-orient the noise grid without skewing, so Y points up the main diagonal,
        // and the planes formed by XZ are moved far out of alignment with the cube faces.
        double xz = x + z;
        double s2 = xz * ROTATE3_ORTHOGONALIZER;
        double yy = y * ROOT3OVER3;
        double xr = x + s2 + yy;
        double zr = z + s2 + yy;
        double yr = xz * -ROOT3OVER3 + yy;

        return sampleDistance2Sub(seed, xr, yr, zr);
    }

    public static float sampleDistance2Sub(long seed, double x, double y, double z) {

        // Lowest axis positions for cells that could contain points in range.
        int xBaseStart = Mth.floor(x - MAX_DISTANCE_TO_CLOSEST);
        int yBaseStart = Mth.floor(y - MAX_DISTANCE_TO_CLOSEST);
        int zBaseStart = Mth.floor(z - MAX_DISTANCE_TO_CLOSEST);

        // Axis loop ranges.
        int xRange = Mth.floor(x + MAX_DISTANCE_TO_CLOSEST) - xBaseStart;
        int yRange = Mth.floor(y + MAX_DISTANCE_TO_CLOSEST) - yBaseStart;
        int zRange = Mth.floor(z + MAX_DISTANCE_TO_CLOSEST) - zBaseStart;

        // Offset from the lowest corner of the loop start cell.
        float xiStart = (float)(x - xBaseStart);
        float yiStart = (float)(y - yBaseStart);
        float ziStart = (float)(z - zBaseStart);

        // Hash prime pre-multiplications for the loop start cell.
        long xStartPrimed = xBaseStart * PRIME_X;
        long yStartPrimed = yBaseStart * PRIME_Y;
        long zStartPrimed = zBaseStart * PRIME_Z;

        // Values we want to know.
        float distSq1 = MAX_DISTANCE_SQUARED_TO_CLOSEST;
        float distSq2 = MAX_DISTANCE_SQUARED_TO_CLOSEST;

        // Loop variables.
        int ix = 0, iy = 0, iz = 0;
        float xi = xiStart, yi = yiStart, zi = ziStart;
        long xPrimed = xStartPrimed, yPrimed = yStartPrimed, zPrimed = zStartPrimed;

        for (;;) {
            float distSqHere = calculateDistanceSquared(seed, xPrimed, yPrimed, zPrimed, xi, yi, zi);

            // Update the closest distance-squared values given the distance-squared to the current cell's point.
            if (distSqHere < distSq2) {
                distSq2 = distSqHere;
                if (distSqHere < distSq1) {
                    distSq2 = distSq1;
                    distSq1 = distSqHere;
                }
            }

            // advance 3D loop variables.
            ix++;
            xi -= 1;
            xPrimed += PRIME_X;
            if (ix <= xRange) continue;
            ix = 0;
            xi = xiStart;
            xPrimed = xStartPrimed;
            iy++;
            yi -= 1;
            yPrimed += PRIME_Y;
            if (iy <= yRange) continue;
            iy = 0;
            yi = yiStart;
            yPrimed = yStartPrimed;
            iz++;
            zi -= 1;
            zPrimed += PRIME_Z;
            if (iz > zRange) break;
        }

        // Hardcoded F2-F1 (Distance2Sub). Can refactor to add generality as needed.
        return Mth.sqrt(distSq2) - Mth.sqrt(distSq1);
    }

    private static float calculateDistanceSquared(long seed, long xPrimed, long yPrimed, long zPrimed, float xi, float yi, float zi) {
        long hash = hash(seed, xPrimed, yPrimed, zPrimed);

        // Hash -> jitter within the unit cube.
        float jx = (((hash >> 43) ^ hash) & JITTER_HASH_MASK) * JITTER_HASH_RESCALE;
        float jy = (((hash >> 43) ^ (hash >> 22)) & JITTER_HASH_MASK) * JITTER_HASH_RESCALE;
        float jz = ((hash >> 43) & JITTER_HASH_MASK) * JITTER_HASH_RESCALE;

        // Squared Euclidean distance from noise sampling input point.
        return Mth.square(xi - jx) + Mth.square(yi - jy) + Mth.square(zi - jz);
    }

    private static long hash(long seed, long xPrimed, long yPrimed, long zPrimed) {
        long hash = seed ^ xPrimed ^ yPrimed ^ zPrimed;
        hash *= HASH_MULTIPLIER;
        return hash;
    }

}
