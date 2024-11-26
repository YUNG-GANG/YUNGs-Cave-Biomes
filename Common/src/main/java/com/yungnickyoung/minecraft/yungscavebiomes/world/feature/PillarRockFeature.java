package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class PillarRockFeature extends Feature<SimpleBlockConfiguration> {

    // Radius range. Smaller radii are made more common than larger in this generator.
    private static final int RADIUS_MIN = 5;
    private static final int RADIUS_MAX = 10;

    // Height is chosen relative to the radius.
    private static final float HEIGHT_RELATIVE_TO_RADIUS_MIN = 3.0f / 2.0f;
    private static final float HEIGHT_RELATIVE_TO_RADIUS_MAX = 2.0f;

    // The height of the top section is also chosen relative to the radius.
    private static final float TOP_HEIGHT_PROPORTION = 0.3f;

    // Where the top section is cut from a hemisphere, before vertical rescaling. Determines taper at crease.
    private static final float TOP_START_HEMISPHERE_OFFSET_MIN = 0.875f;
    private static final float TOP_START_HEMISPHERE_OFFSET_MAX = 0.9375f;

    // Where the bottom section is cut from a hemisphere, before vertical rescaling. Determines taper at crease.
    private static final float BOTTOM_START_HEMISPHERE_OFFSET_MIN = 0.25f;
    private static final float BOTTOM_START_HEMISPHERE_OFFSET_MAX = 0.5f;

    // Configure noise
    private static final double NOISE_FREQUENCY_XZ = 0.1;
    private static final double NOISE_FREQUENCY_Y = NOISE_FREQUENCY_XZ / 1.2;
    public static final float NOISE_MODULATION_AMOUNT = 0.6f;
    private static final long NOISE_SEED_FLIP_MASK = 0x3A1CB2F559143519L;

    public PillarRockFeature(Codec<SimpleBlockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        SimpleBlockConfiguration config = context.config();
        BlockState state = config.toPlace().getState(random, context.origin());
        long noiseSeed = level.getSeed() ^ NOISE_SEED_FLIP_MASK;

        // Choose radius in a way that fits the range but makes smaller sizes more common.
        float radXZ = Mth.square(random.nextFloat()) * (RADIUS_MAX - RADIUS_MIN) + RADIUS_MIN;

        // Height is chosen relative to the XZ radius.
        float height = Mth.nextFloat(random, HEIGHT_RELATIVE_TO_RADIUS_MIN, HEIGHT_RELATIVE_TO_RADIUS_MAX) * radXZ;

        // Height of top portion is also relative to the XZ radius.
        float topHeight = radXZ * TOP_HEIGHT_PROPORTION;
        float bottomHeight = height - topHeight;
        float creasePositionY = height * 0.5f - topHeight;

        // The top and bottom sections are offset and vertically-rescaled hemispheres. These offsets determine the initial taper.
        float topStartHemisphereOffset =  Mth.nextFloat(random, TOP_START_HEMISPHERE_OFFSET_MIN, TOP_START_HEMISPHERE_OFFSET_MAX);
        float bottomStartHemisphereOffset =  Mth.nextFloat(random, BOTTOM_START_HEMISPHERE_OFFSET_MIN, BOTTOM_START_HEMISPHERE_OFFSET_MAX);

        // To map dy to startHemisphereOffset at the crease, 1 at the boundary.
        float dyTopMultiplier = (1.0f - topStartHemisphereOffset) / topHeight;
        float dyBottomMultiplier = (bottomStartHemisphereOffset - 1.0f) / bottomHeight;
        float dyTopOffset = topStartHemisphereOffset - dyTopMultiplier * creasePositionY;
        float dyBottomOffset = bottomStartHemisphereOffset - dyBottomMultiplier * creasePositionY;

        float dySqTopAtCrease = Mth.square(creasePositionY * dyTopMultiplier + dyTopOffset);
        float dySqBottomAtCrease = Mth.square(creasePositionY * dyBottomMultiplier + dyBottomOffset);

        // To rescale horizontal distance squared so the horizontal boundary is in the right place too.
        float topStartDistSqXZCorrection = (1.0f - dySqTopAtCrease) / (radXZ * radXZ);
        float bottomStartDistSqXZCorrection = (1.0f - dySqBottomAtCrease) / (radXZ * radXZ);

        // To map the final sphere falloffs to 1 at the center of the crease, 0 at the boundary.
        float falloffCurveTopMultiplier = -1.0f / (1.0f - dySqTopAtCrease);
        float falloffCurveBottomMultiplier = -1.0f / (1.0f - dySqBottomAtCrease);
        float falloffCurveTopOffset = 1.0f - falloffCurveTopMultiplier * dySqTopAtCrease;
        float falloffCurveBottomOffset = 1.0f - falloffCurveBottomMultiplier * dySqBottomAtCrease;

        int radYBound = Mth.ceil(height * 0.5f);
        int radXZBound = Mth.ceil(radXZ);
        for (int dy = -radYBound; dy <= radYBound; dy++) {
            boolean isTop = (dy >= creasePositionY);

            float dySq = isTop ?
                    Mth.square(dy * dyTopMultiplier + dyTopOffset) :
                    Mth.square(dy * dyBottomMultiplier + dyBottomOffset);

            for (int dz = -radXZBound; dz <= radXZBound; dz++) {
                for (int dx = -radXZBound; dx <= radXZBound; dx++) {

                    float distSqXZ = (dx * dx + dz * dz) * (isTop ?
                            topStartDistSqXZCorrection :
                            bottomStartDistSqXZCorrection
                    );
                    float distSq = distSqXZ + dySq;

                    float baseDensityHere = isTop ?
                            distSq * falloffCurveTopMultiplier + falloffCurveTopOffset :
                            distSq * falloffCurveBottomMultiplier + falloffCurveBottomOffset;

                    boolean isInRange = (baseDensityHere >= NOISE_MODULATION_AMOUNT);
                    if (!isInRange) {
                        float noiseHere = OpenSimplex2S.noise3_ImproveXZ(noiseSeed,
                                (dx + origin.getX()) * NOISE_FREQUENCY_XZ,
                                (dy + origin.getY()) * NOISE_FREQUENCY_Y,
                                (dz + origin.getZ()) * NOISE_FREQUENCY_XZ
                        ) * (0.5f * NOISE_MODULATION_AMOUNT) + (0.5f * NOISE_MODULATION_AMOUNT);
                        float densityHere = baseDensityHere - noiseHere;
                        isInRange = (densityHere > 0.0f);
                    }

                    if (isInRange) {
                        BlockPos local = origin.offset(dx, dy, dz);
                        level.setBlock(local, state, 3);
                    }
                }
            }
        }

        return false;
    }
}
