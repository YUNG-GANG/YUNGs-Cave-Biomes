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
import java.util.Queue;

public class WaterSurfaceIceFragmentFeature extends Feature<NoneFeatureConfiguration> {
    public WaterSurfaceIceFragmentFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static final int MAX_FLOOD_RADIUS = 8;
    private static final int MAX_FLOOD_DIAMETER = MAX_FLOOD_RADIUS * 2 + 1;
    private static final float ICE_FRAGMENT_SEPARATION = 0.15f;
    private static final float ICE_FRAGMENT_NOISE_FREQUENCY = 1.0f / 12.0f;

    // N.5 radii produce nicer circles: https://www.redblobgames.com/grids/circle-drawing/
    private static final float EFFECTIVE_MAX_RANGE_RADIUS_SQ = (int)((MAX_FLOOD_RADIUS + 0.5f) * (MAX_FLOOD_RADIUS + 0.5f));

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        // Set up for flood-fill over the ice surface shard noise
        Queue<BlockPos> floodQueue = new ArrayDeque<>(MAX_FLOOD_DIAMETER * MAX_FLOOD_DIAMETER);
        boolean[] visited = new boolean[MAX_FLOOD_DIAMETER * MAX_FLOOD_DIAMETER];

        // Start flood-fill the feature origin
        floodQueue.add(origin);

        // TODO this can be moved to a (thread-safe) static initialization
        FastNoise iceFragmentNoise = new FastNoise((int)(level.getSeed() >> 32));
        iceFragmentNoise.SetCellularDistanceFunction(FastNoise.CellularDistanceFunction.Euclidean);
        iceFragmentNoise.SetCellularReturnType(FastNoise.CellularReturnType.Distance2Sub);
        iceFragmentNoise.SetFrequency(ICE_FRAGMENT_NOISE_FREQUENCY);

        // Perform the flood-fill
        while (!floodQueue.isEmpty()) {
            BlockPos currentPos = floodQueue.poll();
            BlockState currentState = level.getBlockState(currentPos);

            // Nothing to do here if it's not water or ice.
            if (!currentState.is(Blocks.WATER) && !currentState.is(Blocks.ICE)) continue;

            // TODO do we need to check if it's not a water-logged block?

            // 3D noise so two pools offset only vertically don't show identical patterns.
            float iceShardNoiseValue = iceFragmentNoise.GetCellular(currentPos.getX(), currentPos.getY(), currentPos.getZ());

            // If we're in the boundary between two ice shards, nothing to do here.
            if (iceShardNoiseValue <= (ICE_FRAGMENT_SEPARATION - 1)) continue;

            // We know this one is ice now.
            level.setBlock(currentPos, Blocks.ICE.defaultBlockState(), 2);

            // Now consider propagating into the four neighbors.
            considerNeighbor(floodQueue, visited, origin, currentPos.east());
            considerNeighbor(floodQueue, visited, origin, currentPos.west());
            considerNeighbor(floodQueue, visited, origin, currentPos.south());
            considerNeighbor(floodQueue, visited, origin, currentPos.north());
        }

        return false;
    }

    private void considerNeighbor(Queue<BlockPos> floodQueue, boolean[] visited, BlockPos origin, BlockPos neighborPos) {

        // Delta from feature placement center.
        int dx = neighborPos.getX() - origin.getX();
        int dz = neighborPos.getZ() - origin.getZ();

        // Initial out-of-range check.
        if (dx < -MAX_FLOOD_RADIUS || dx > MAX_FLOOD_RADIUS || dz < -MAX_FLOOD_RADIUS || dz > MAX_FLOOD_RADIUS) return;

        // Don't add to the queue if it's already been visited. Otherwise mark it as such.
        int visitedIndex = (dz + MAX_FLOOD_RADIUS) * MAX_FLOOD_DIAMETER + (dx + MAX_FLOOD_RADIUS);
        if (visited[visitedIndex]) return;
        visited[visitedIndex] = true;

        // Keep out-of-range borders looking nice in all directions by bounding the search to a circle.
        if (dx * dx + dz * dz > EFFECTIVE_MAX_RANGE_RADIUS_SQ) return;

        // Now it's ready to be processed at some point.
        floodQueue.add(neighborPos);
    }
}
