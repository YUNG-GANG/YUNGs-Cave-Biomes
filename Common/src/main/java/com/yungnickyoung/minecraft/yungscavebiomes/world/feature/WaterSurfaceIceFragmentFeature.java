package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.CellularNoise;
import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class WaterSurfaceIceFragmentFeature extends Feature<NoneFeatureConfiguration> {
    public WaterSurfaceIceFragmentFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    // Flood-fill configuration.
    private static final int MIN_MAX_FLOOD_RADIUS = 8;
    private static final int MAX_MAX_FLOOD_RADIUS = 12;
    private static final int EFFECTIVE_MAX_FLOOD_RADIUS = MAX_MAX_FLOOD_RADIUS - 1;
    private static final int EFFECTIVE_MAX_FLOOD_DIAMETER = EFFECTIVE_MAX_FLOOD_RADIUS * 2 + 1;
    private static final int MAX_FLOOD_RADIUS_RANGE = MAX_MAX_FLOOD_RADIUS - MIN_MAX_FLOOD_RADIUS;
    private static final float FLOOD_BOUNDARY_NOISE_FREQUENCY = 1.0f / 10.0f;
    private static final long FLOOD_BOUNDARY_NOISE_SEED_FLIP = -0x3086FA4ADCEE58ADL;

    // Ice fragment noise parameters.
    private static final float ICE_FRAGMENT_SEPARATION = 0.15f;
    private static final float ICE_FRAGMENT_NOISE_FREQUENCY = 1.0f / 12.0f;
    private static final float DOMAIN_WARP_FREQUENCY = 1.0f / 12.0f;
    private static final float DOMAIN_WARP_AMPLITUDE = 4f;
    private static final long WARP_NOISE_SEED_FLIP = 0x34E3E6E2D932825FL;
    private static final long CELL_NOISE_SEED_FLIP = 0x5AAEE84593A93C88L;

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        // Call the thread-local allocation to perform the flood-fill.
        iceFragmentFillerThreadLocal.get().execute(origin, level);

        return false;
    }

    private static final ThreadLocal<IceFragmentFiller> iceFragmentFillerThreadLocal = ThreadLocal.withInitial(IceFragmentFiller::new);

    private static class IceFragmentFiller {
        private final Queue<BlockPos> floodQueue;
        private final boolean[] visited;
        private final Vector3f domainWarpVector;

        public IceFragmentFiller() {
            this.floodQueue = new ArrayDeque<>(EFFECTIVE_MAX_FLOOD_DIAMETER * EFFECTIVE_MAX_FLOOD_DIAMETER);
            this.visited = new boolean[EFFECTIVE_MAX_FLOOD_DIAMETER * EFFECTIVE_MAX_FLOOD_DIAMETER];
            this.domainWarpVector = new Vector3f();
        }

        public void execute(BlockPos origin, WorldGenLevel level) {
            long seed = level.getSeed();
            long noiseSeedWarp = seed ^ WARP_NOISE_SEED_FLIP;
            long noiseSeedCell = seed ^ CELL_NOISE_SEED_FLIP;
            long noiseSeedFlood = seed ^ FLOOD_BOUNDARY_NOISE_SEED_FLIP;

            // Initialize for a new flood-fill over the ice surface fragment noise
            this.floodQueue.clear();
            Arrays.fill(visited, false);

            // Start flood-fill at the feature origin
            this.floodQueue.add(origin);

            // Perform the flood-fill
            while (!this.floodQueue.isEmpty()) {
                BlockPos currentPos = this.floodQueue.poll();
                BlockState currentState = level.getBlockState(currentPos);

                // Nothing to do here if it's not water or ice.
                if (!currentState.is(Blocks.WATER) && !currentState.is(Blocks.ICE)) continue;

                // Get domain warping vector for cellular noise using a purpose-designed noise function.
                OpenSimplex2S.vec3Noise3_ImproveXZ(
                        noiseSeedWarp,
                        currentPos.getX() * DOMAIN_WARP_FREQUENCY,
                        currentPos.getY() * DOMAIN_WARP_FREQUENCY,
                        currentPos.getZ() * DOMAIN_WARP_FREQUENCY,
                        this.domainWarpVector
                );

                // Evaluate cellular noise at the warped position.
                // 3D noise is used so that two water bodies offset only vertically don't show identical ice patterns.
                float iceShardNoiseValue = CellularNoise.sampleDistance2Sub_ImproveXZ(
                        noiseSeedCell,
                        currentPos.getX() * ICE_FRAGMENT_NOISE_FREQUENCY + this.domainWarpVector.x() * (ICE_FRAGMENT_NOISE_FREQUENCY * DOMAIN_WARP_AMPLITUDE),
                        currentPos.getY() * ICE_FRAGMENT_NOISE_FREQUENCY + this.domainWarpVector.y() * (ICE_FRAGMENT_NOISE_FREQUENCY * DOMAIN_WARP_AMPLITUDE),
                        currentPos.getZ() * ICE_FRAGMENT_NOISE_FREQUENCY + this.domainWarpVector.z() * (ICE_FRAGMENT_NOISE_FREQUENCY * DOMAIN_WARP_AMPLITUDE)
                );

                // If we're in the boundary between two ice shards, nothing to do here.
                if (iceShardNoiseValue <= ICE_FRAGMENT_SEPARATION) continue;

                // We know this one is ice now.
                level.setBlock(currentPos, Blocks.ICE.defaultBlockState(), 2);

                // Now consider propagating into the four neighbors.
                considerNeighbor(noiseSeedFlood, origin, currentPos.east());
                considerNeighbor(noiseSeedFlood, origin, currentPos.west());
                considerNeighbor(noiseSeedFlood, origin, currentPos.south());
                considerNeighbor(noiseSeedFlood, origin, currentPos.north());
            }
        }

        private void considerNeighbor(long noiseSeedFlood, BlockPos origin, BlockPos neighborPos) {

            // Delta from feature placement center.
            int dx = neighborPos.getX() - origin.getX();
            int dz = neighborPos.getZ() - origin.getZ();

            // Initial out-of-range check.
            if (dx < -EFFECTIVE_MAX_FLOOD_RADIUS || dx > EFFECTIVE_MAX_FLOOD_RADIUS || dz < -EFFECTIVE_MAX_FLOOD_RADIUS || dz > EFFECTIVE_MAX_FLOOD_RADIUS) return;

            // Don't add to the queue if it's already been visited. Otherwise mark it as such.
            int visitedIndex = (dz + EFFECTIVE_MAX_FLOOD_RADIUS) * EFFECTIVE_MAX_FLOOD_DIAMETER + (dx + EFFECTIVE_MAX_FLOOD_RADIUS);
            if (this.visited[visitedIndex]) return;
            this.visited[visitedIndex] = true;

            // Keep out-of-range borders looking nice in all directions by bounding the search to a noisy circle.
            float distSq = dx * dx + dz * dz;
            if (distSq > EFFECTIVE_MAX_FLOOD_RADIUS * EFFECTIVE_MAX_FLOOD_RADIUS) return;
            if (distSq > MIN_MAX_FLOOD_RADIUS * MIN_MAX_FLOOD_RADIUS) {
                float noiseValue = OpenSimplex2S.noise3_ImproveXZ(
                        noiseSeedFlood,
                        (origin.getX() + dx) * FLOOD_BOUNDARY_NOISE_FREQUENCY,
                        origin.getY() * FLOOD_BOUNDARY_NOISE_FREQUENCY,
                        (origin.getZ() + dz) * FLOOD_BOUNDARY_NOISE_FREQUENCY
                );
                float noisyRadius = noiseValue * (0.5f * MAX_FLOOD_RADIUS_RANGE) + (0.5f * MAX_FLOOD_RADIUS_RANGE + MIN_MAX_FLOOD_RADIUS);
                if (distSq > noisyRadius * noisyRadius) return;
            }

            // We've figured out we do want to process this position in the flood-fill.
            this.floodQueue.add(neighborPos);
        }
    }
}
