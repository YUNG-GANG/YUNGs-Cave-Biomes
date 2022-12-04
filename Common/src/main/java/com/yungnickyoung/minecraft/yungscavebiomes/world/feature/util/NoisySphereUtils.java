package com.yungnickyoung.minecraft.yungscavebiomes.world.feature.util;

import com.yungnickyoung.minecraft.yungscavebiomes.world.noise.OpenSimplex2S;
import net.minecraft.core.BlockPos;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NoisySphereUtils {

    public static final class NoisySphereIterable implements Iterable<BlockPos> {
        private final long noiseSeed;
        private final BlockPos origin;
        private final double noiseFrequencyXZ, noiseFrequencyY;
        private final int radiusMax, radiusMinSq, radiusMaxSq;
        private final float noiseRadiusBase, noiseRadiusVariation;

        public NoisySphereIterable(BlockPos origin, long noiseSeed, double noiseFrequencyXZ, double noiseFrequencyY, int radiusMin, int radiusMax) {
            this.origin = origin;
            this.noiseSeed = noiseSeed;
            this.noiseFrequencyXZ = noiseFrequencyXZ;
            this.noiseFrequencyY = noiseFrequencyY;
            this.radiusMax = radiusMax;
            this.radiusMinSq = radiusMin * radiusMin;
            this.radiusMaxSq = radiusMax * radiusMax;
            this.noiseRadiusBase = (radiusMax + radiusMin) * 0.5f;
            this.noiseRadiusVariation = (radiusMax - radiusMin) * 0.5f;
        }

        @Override
        public Iterator<BlockPos> iterator() {
            return new NoisySphereIterator();
        }

        public class NoisySphereIterator implements Iterator<BlockPos> {
            private int dx, dy, dz;
            private BlockPos next;

            public NoisySphereIterator() {
                this.dx = -radiusMax;
                this.dy = -radiusMax;
                this.dz = -radiusMax;
                update();
            }

            @Override
            public boolean hasNext() {
                return (dz <= radiusMax);
            }

            @Override
            public BlockPos next() {
                if (!hasNext()) throw new NoSuchElementException();
                BlockPos here = next;
                update();
                return here;
            }

            private void update() {
                while (true) {
                    int distSq = dx * dx + dy * dy + dz * dz;
                    next = origin.offset(dx, dy, dz);

                    boolean isInRange = (distSq < radiusMinSq);
                    if (!isInRange && distSq < radiusMaxSq) {
                        float noiseValue = OpenSimplex2S.noise3_ImproveXZ(
                                noiseSeed,
                                next.getX() * noiseFrequencyXZ,
                                next.getY() * noiseFrequencyY,
                                next.getZ() * noiseFrequencyXZ
                        );
                        float noiseRadius = noiseRadiusBase + noiseRadiusVariation * noiseValue;
                        isInRange = (distSq < noiseRadius * noiseRadius);
                    }

                    boolean loopContinues = advanceOffsets();
                    if (isInRange || !loopContinues) break;
                }
            }

            boolean advanceOffsets() {
                dy++;
                if (dy <= radiusMax) return true;
                dy = -radiusMax;
                dx++;
                if (dx <= radiusMax) return true;
                dx = -radiusMax;
                dz++;
                return (dz <= radiusMax);
            }
        }
    }

}
