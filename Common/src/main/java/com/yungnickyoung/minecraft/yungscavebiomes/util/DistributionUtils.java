package com.yungnickyoung.minecraft.yungscavebiomes.util;

import net.minecraft.util.Mth;

import java.util.Random;

public class DistributionUtils {
    /**
     * Generates a random point within an ellipsoid centered at the origin.
     * The distribution is biased with points more likely to be far from the center.
     * @param radiusXZ The radius of the ellipsoid in the XZ plane.
     * @param radiusY The radius of the ellipsoid in the Y direction.
     * @param random The random number generator to use.
     * @param callback A function to apply to the generated point.
     * @return The result of applying the callback to the generated point.
     * @param <R> The return type of the callback.
     */
    public static <R> R ellipsoidCenterBiasedSpread(float radiusXZ, float radiusY, Random random, CoordinateFunction<R> callback) {
        float sphereY = random.nextFloat(-1.0f, 1.0f);
        float sphereTheta = random.nextFloat(Mth.TWO_PI);
        float sphereXZScale = Mth.sqrt(1.0f - sphereY * sphereY);

        // Equivalent in distribution to Mth.sqrt(random.nextFloat()).
        // Note that a cube root here would result in a uniform spherical distribution.
        float radiusScale = 1 - Math.abs(random.nextFloat() - random.nextFloat());

        return callback.apply(
                radiusScale * sphereXZScale * radiusXZ * Mth.cos(sphereTheta),
                radiusScale * sphereY * radiusY,
                radiusScale * sphereXZScale * radiusXZ * Mth.sin(sphereTheta)
        );
    }

    public interface CoordinateFunction<R> {
        R apply(float x, float y, float z);
    }
}
