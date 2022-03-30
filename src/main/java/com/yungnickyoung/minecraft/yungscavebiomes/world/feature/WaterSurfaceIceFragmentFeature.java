package com.yungnickyoung.minecraft.yungscavebiomes.world.feature;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsapi.noise.FastNoise;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class WaterSurfaceIceFragmentFeature extends Feature<NoneFeatureConfiguration> {
    public WaterSurfaceIceFragmentFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    // Flood-fill configuration.
    private static final int MAX_FLOOD_RADIUS = 8;
    private static final int MAX_FLOOD_DIAMETER = MAX_FLOOD_RADIUS * 2 + 1;

    // Ice fragment cellular noise parameters.
    private static final float ICE_FRAGMENT_SEPARATION = 0.2f;
    private static final float ICE_FRAGMENT_NOISE_FREQUENCY = 1.0f / 12.0f;

    // N.5 radii produce nicer circles: https://www.redblobgames.com/grids/circle-drawing/
    private static final int EFFECTIVE_MAX_RANGE_RADIUS_SQ = (int)((MAX_FLOOD_RADIUS + 0.5f) * (MAX_FLOOD_RADIUS + 0.5f));

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        // Call the thread-local allocation to perform the flood-fill.
        iceFragmentFillerThreadLocal.get().execute(origin, level);

        return false;
    }

    private static final ThreadLocal<IceFragmentFiller> iceFragmentFillerThreadLocal = new ThreadLocal<>() {
        @Override
        protected IceFragmentFiller initialValue() {
            return new IceFragmentFiller();
        }
    };

    private static class IceFragmentFiller {
        private final Queue<BlockPos> floodQueue;
        private final boolean[] visited;
        private final FastNoise iceFragmentNoise;

        public IceFragmentFiller() {
            this.floodQueue = new ArrayDeque<>(MAX_FLOOD_DIAMETER * MAX_FLOOD_DIAMETER);
            this.visited = new boolean[MAX_FLOOD_DIAMETER * MAX_FLOOD_DIAMETER];
            this.iceFragmentNoise = new FastNoise();
            this.iceFragmentNoise.SetCellularDistanceFunction(FastNoise.CellularDistanceFunction.Euclidean);
            this.iceFragmentNoise.SetCellularReturnType(FastNoise.CellularReturnType.Distance2Sub);
            this.iceFragmentNoise.SetFrequency(ICE_FRAGMENT_NOISE_FREQUENCY);
        }

        public void execute(BlockPos origin, WorldGenLevel level) {

            // Initialize for a new flood-fill over the ice surface fragment noise
            this.floodQueue.clear();
            Arrays.fill(visited, false);

            // Set/update the noise seed. Typically this will only happen once,
            // unless the feature is being used simultaneously by multiple generators.
            int noiseSeed = (int)level.getSeed();
            if (this.iceFragmentNoise.GetSeed() != noiseSeed) this.iceFragmentNoise.SetSeed(noiseSeed);

            // Start flood-fill at the feature origin
            this.floodQueue.add(origin);

            // Perform the flood-fill
            while (!this.floodQueue.isEmpty()) {
                BlockPos currentPos = this.floodQueue.poll();
                BlockState currentState = level.getBlockState(currentPos);

                // Nothing to do here if it's not water or ice.
                if (!currentState.is(Blocks.WATER) && !currentState.is(Blocks.ICE)) continue;

                // 3D noise so two pools offset only vertically don't show identical patterns.
                float iceShardNoiseValue = this.iceFragmentNoise.GetCellular(currentPos.getX(), currentPos.getY(), currentPos.getZ());

                // If we're in the boundary between two ice shards, nothing to do here.
                if (iceShardNoiseValue <= (ICE_FRAGMENT_SEPARATION - 1)) continue;

                // We know this one is ice now.
                level.setBlock(currentPos, Blocks.ICE.defaultBlockState(), 2);

                // Now consider propagating into the four neighbors.
                considerNeighbor(origin, currentPos.east());
                considerNeighbor(origin, currentPos.west());
                considerNeighbor(origin, currentPos.south());
                considerNeighbor(origin, currentPos.north());
            }
        }

        private void considerNeighbor(BlockPos origin, BlockPos neighborPos) {

            // Delta from feature placement center.
            int dx = neighborPos.getX() - origin.getX();
            int dz = neighborPos.getZ() - origin.getZ();

            // Initial out-of-range check.
            if (dx < -MAX_FLOOD_RADIUS || dx > MAX_FLOOD_RADIUS || dz < -MAX_FLOOD_RADIUS || dz > MAX_FLOOD_RADIUS) return;

            // Don't add to the queue if it's already been visited. Otherwise mark it as such.
            int visitedIndex = (dz + MAX_FLOOD_RADIUS) * MAX_FLOOD_DIAMETER + (dx + MAX_FLOOD_RADIUS);
            if (this.visited[visitedIndex]) return;
            this.visited[visitedIndex] = true;

            // Keep out-of-range borders looking nice in all directions by bounding the search to a circle.
            if (dx * dx + dz * dz > EFFECTIVE_MAX_RANGE_RADIUS_SQ) return;

            // We've figured out we do want to process this position in the flood-fill.
            this.floodQueue.add(neighborPos);
        }
    }
}
