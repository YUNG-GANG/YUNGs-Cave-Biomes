//package com.yungnickyoung.minecraft.yungscavebiomes.mixin.client;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.BufferBuilder;
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.Tesselator;
//import com.mojang.blaze3d.vertex.VertexFormat;
//import com.mojang.math.Matrix4f;
//import com.mojang.math.Vector3f;
//import com.yungnickyoung.minecraft.yungscavebiomes.module.BiomeModule;
//import com.yungnickyoung.minecraft.yungscavebiomes.module.MobEffectModule;
//import com.yungnickyoung.minecraft.yungscavebiomes.module.ParticleTypeModule;
//import net.minecraft.client.Camera;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.particle.SpriteSet;
//import net.minecraft.client.renderer.GameRenderer;
//import net.minecraft.client.renderer.LevelRenderer;
//import net.minecraft.client.renderer.LightTexture;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.particles.ParticleOptions;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import net.minecraft.world.level.BlockAndTintGetter;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.levelgen.Heightmap;
//import org.jetbrains.annotations.Nullable;
//import org.spongepowered.asm.mixin.Final;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.util.Random;
//
//@Mixin(LevelRenderer.class)
//public abstract class MixinLevelRenderer {
//
//    @Shadow
//    @Final
//    private Minecraft minecraft;
//
//    @Shadow
//    private int ticks;
//
//    @Shadow
//    @Final
//    private float[] rainSizeX;
//
//    @Shadow
//    @Final
//    private float[] rainSizeZ;
//
//    @Shadow
//    public static int getLightColor(BlockAndTintGetter $$0, BlockPos $$1) {
//        return 0;
//    }
//
//    @Shadow
//    @Final
//    private static ResourceLocation RAIN_LOCATION;
//
//    @Shadow
//    @Final
//    private static ResourceLocation SNOW_LOCATION;
//
//    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V"))
//    private void yungscavebiomes_renderSandstormParticles(PoseStack matrixStack, float $$1, long $$2, boolean $$3, Camera $$4, GameRenderer $$5, LightTexture $$6, Matrix4f $$7, CallbackInfo ci) {
//        this.renderSandstorm($$6, );
//    }
//
//    @Unique
//    private void renderSandstorm(LightTexture lightTexture, float partialTicks, double x, double y, double z) {
//        float rainLevel = this.minecraft.level.getRainLevel(partialTicks);
//        if (rainLevel > 0.0F) {
//            lightTexture.turnOnLightLayer();
//            Level level = this.minecraft.level;
//            int blockX = Mth.floor(x);
//            int blockY = Mth.floor(y);
//            int blockZ = Mth.floor(z);
//            Tesselator tesselator = Tesselator.getInstance();
//            BufferBuilder bufferBuilder = tesselator.getBuilder();
//            RenderSystem.disableCull();
////            RenderSystem.enableBlend();
////            RenderSystem.defaultBlendFunc();
//            RenderSystem.disableBlend();
//            RenderSystem.enableDepthTest();
//            int rainRenderDistance = 5;
//            if (Minecraft.useFancyGraphics()) {
//                rainRenderDistance = 10;
//            }
//
//            RenderSystem.depthMask(Minecraft.useShaderTransparency());
//            int startMarker = -1;
//            float currTicks = (float) this.ticks + partialTicks;
//            RenderSystem.setShader(GameRenderer::getParticleShader);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
//
//            for (int currZ = blockZ - rainRenderDistance; currZ <= blockZ + rainRenderDistance; ++currZ) {
//                for (int currX = blockX - rainRenderDistance; currX <= blockX + rainRenderDistance; ++currX) {
//                    int rainPosIndex = (currZ - blockZ + 16) * 32 + currX - blockX + 16;
//                    double rainSizeX = (double) this.rainSizeX[rainPosIndex] * 0.5D;
//                    double rainSizeZ = (double) this.rainSizeZ[rainPosIndex] * 0.5D;
//                    mutable.set(currX, y, currZ);
//                    if (!level.getBiome(mutable).is(BiomeModule.LOST_CAVES.getResourceKey())) {
//                        continue;
//                    }
//
//                    int surfaceHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING, currX, currZ);
//
//                    int minRainY = blockY - rainRenderDistance;
//                    int maxRainY = blockY + rainRenderDistance;
//
//                    if (minRainY < surfaceHeight) {
//                        minRainY = surfaceHeight;
//                    }
//
//                    if (maxRainY < surfaceHeight) {
//                        maxRainY = surfaceHeight;
//                    }
//
//                    int renderY = Math.max(surfaceHeight, blockY);
//
//                    // Ensures at least the maxRainY is above surface.
//                    // No need to render rain if it's all below the surface.
//                    if (minRainY != maxRainY) {
//                        long seed = currX * currX * 3121L + currX * 45238971L ^ currZ * currZ * 418711L + currZ * 13761L;
//                        Random random = new Random(seed);
//                        mutable.set(currX, minRainY, currZ);
//                        // Begin rendering rain
//                        if (startMarker != 0) {
//
//                            startMarker = 0;
////                            RenderSystem.setShaderTexture(0, RAIN_LOCATION);
//                            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
//                            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
//                        }
//
//                        int $$27 = (int) (this.ticks + seed & 31);
//                        float $$28 = -((float) $$27 + partialTicks) / 32.0F * (3.0F + random.nextFloat());
//                        double $$29 = (double) currX + 0.5D - x;
//                        double $$30 = (double) currZ + 0.5D - z;
//                        float $$31 = (float) Math.sqrt($$29 * $$29 + $$30 * $$30) / (float) rainRenderDistance;
//                        float alpha = ((1.0F - $$31 * $$31) * 0.5F + 0.5F) * rainLevel;
//                        mutable.set(currX, renderY, currZ);
//                        int lightColor = getLightColor(level, mutable);
//                        bufferBuilder
//                                .vertex((double) currX - x - rainSizeX + 0.5D, (double) maxRainY - y, (double) currZ - z - rainSizeZ + 0.5D)
//                                .uv(0.0F, (float) minRainY * 0.25F + $$28)
//                                .color(1.0F, 1.0F, 1.0F, alpha)
//                                .uv2(lightColor)
//                                .endVertex();
//                        bufferBuilder
//                                .vertex((double) currX - x + rainSizeX + 0.5D, (double) maxRainY - y, (double) currZ - z + rainSizeZ + 0.5D)
//                                .uv(1.0F, (float) minRainY * 0.25F + $$28)
//                                .color(1.0F, 1.0F, 1.0F, alpha)
//                                .uv2(lightColor)
//                                .endVertex();
//                        bufferBuilder
//                                .vertex((double) currX - x + rainSizeX + 0.5D, (double) minRainY - y, (double) currZ - z + rainSizeZ + 0.5D)
//                                .uv(1.0F, (float) maxRainY * 0.25F + $$28)
//                                .color(1.0F, 1.0F, 1.0F, alpha)
//                                .uv2(lightColor)
//                                .endVertex();
//                        bufferBuilder
//                                .vertex((double) currX - x - rainSizeX + 0.5D, (double) minRainY - y, (double) currZ - z - rainSizeZ + 0.5D)
//                                .uv(0.0F, (float) maxRainY * 0.25F + $$28)
//                                .color(1.0F, 1.0F, 1.0F, alpha)
//                                .uv2(lightColor)
//                                .endVertex();
//
//                        Vector3f[] $$11 = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
//                        float $$15 = this.getU0();
//                        float $$16 = this.getU1();
//                        float $$17 = this.getV0();
//                        float $$18 = this.getV1();
//                        bufferBuilder
//                                .vertex($$11[0].x(), $$11[0].y(), $$11[0].z())
//                                .uv($$16, $$18)
//                                .color(this.rCol, this.gCol, this.bCol, this.alpha)
//                                .uv2($$19)
//                                .endVertex();
//                        bufferBuilder
//                                .vertex($$11[1].x(), $$11[1].y(), $$11[1].z())
//                                .uv($$16, $$17)
//                                .color(this.rCol, this.gCol, this.bCol, this.alpha)
//                                .uv2($$19)
//                                .endVertex();
//                        bufferBuilder
//                                .vertex($$11[2].x(), $$11[2].y(), $$11[2].z())
//                                .uv($$15, $$17)
//                                .color(this.rCol, this.gCol, this.bCol, this.alpha)
//                                .uv2($$19)
//                                .endVertex();
//                        bufferBuilder
//                                .vertex($$11[3].x(), $$11[3].y(), $$11[3].z())
//                                .uv($$15, $$18)
//                                .color(this.rCol, this.gCol, this.bCol, this.alpha)
//                                .uv2($$19)
//                                .endVertex();
//
//                    }
//                }
//            }
//
//            if (startMarker >= 0) {
//                tesselator.end();
//            }
//
//            RenderSystem.enableCull();
//            RenderSystem.disableBlend();
//            lightTexture.turnOffLightLayer();
//        }
//    }
//
////    @Unique
////    private void renderSandstorm(LightTexture lightTexture, float partialTicks, double x, double y, double z) {
////        float rainLevel = this.minecraft.level.getRainLevel(partialTicks);
////        if (rainLevel > 0.0F) {
////            lightTexture.turnOnLightLayer();
////            Level level = this.minecraft.level;
////            int blockX = Mth.floor(x);
////            int blockY = Mth.floor(y);
////            int blockZ = Mth.floor(z);
////            Tesselator tesselator = Tesselator.getInstance();
////            BufferBuilder bufferBuilder = tesselator.getBuilder();
////            RenderSystem.disableCull();
////            RenderSystem.enableBlend();
////            RenderSystem.defaultBlendFunc();
////            RenderSystem.enableDepthTest();
////            int rainRenderDistance = 5;
////            if (Minecraft.useFancyGraphics()) {
////                rainRenderDistance = 10;
////            }
////
////            RenderSystem.depthMask(Minecraft.useShaderTransparency());
////            int $$13 = -1;
////            float currTicks = (float) this.ticks + partialTicks;
////            RenderSystem.setShader(GameRenderer::getParticleShader);
////            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
////            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
////
////            for (int currZ = blockZ - rainRenderDistance; currZ <= blockZ + rainRenderDistance; ++currZ) {
////                for (int currX = blockX - rainRenderDistance; currX <= blockX + rainRenderDistance; ++currX) {
////                    int rainPosIndex = (currZ - blockZ + 16) * 32 + currX - blockX + 16;
////                    double rainSizeX = (double) this.rainSizeX[rainPosIndex] * 0.5D;
////                    double rainSizeZ = (double) this.rainSizeZ[rainPosIndex] * 0.5D;
////                    mutable.set(currX, y, currZ);
////                    Biome biome = level.getBiome(mutable).value();
////                    if (biome.getPrecipitation() == Biome.Precipitation.NONE) {
////                        continue;
////                    }
////
////                    int surfaceHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING, currX, currZ);
////
////                    int minRainY = blockY - rainRenderDistance;
////                    int maxRainY = blockY + rainRenderDistance;
////
////                    if (minRainY < surfaceHeight) {
////                        minRainY = surfaceHeight;
////                    }
////
////                    if (maxRainY < surfaceHeight) {
////                        maxRainY = surfaceHeight;
////                    }
////
////                    int renderY = Math.max(surfaceHeight, blockY);
////
////                    // Ensures at least the maxRainY is above surface.
////                    // No need to render rain if it's all below the surface.
////                    if (minRainY != maxRainY) {
////                        long seed = currX * currX * 3121L + currX * 45238971L ^ currZ * currZ * 418711L + currZ * 13761L;
////                        Random random = new Random(seed);
////                        mutable.set(currX, minRainY, currZ);
////                        if (biome.warmEnoughToRain(mutable)) {
////                            // Begin rendering rain
////                            if ($$13 != 0) {
////                                if ($$13 >= 0) {
////                                    tesselator.end();
////                                }
////
////                                $$13 = 0;
////                                RenderSystem.setShaderTexture(0, RAIN_LOCATION);
////                                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
////                            }
////
////                            int $$27 = (int) (this.ticks + seed & 31);
////                            float $$28 = -((float) $$27 + partialTicks) / 32.0F * (3.0F + random.nextFloat());
////                            double $$29 = (double) currX + 0.5D - x;
////                            double $$30 = (double) currZ + 0.5D - z;
////                            float $$31 = (float) Math.sqrt($$29 * $$29 + $$30 * $$30) / (float) rainRenderDistance;
////                            float alpha = ((1.0F - $$31 * $$31) * 0.5F + 0.5F) * rainLevel;
////                            mutable.set(currX, renderY, currZ);
////                            int lightColor = getLightColor(level, mutable);
////                            bufferBuilder
////                                    .vertex((double) currX - x - rainSizeX + 0.5D, (double) maxRainY - y, (double) currZ - z - rainSizeZ + 0.5D)
////                                    .uv(0.0F, (float) minRainY * 0.25F + $$28)
////                                    .color(1.0F, 1.0F, 1.0F, alpha)
////                                    .uv2(lightColor)
////                                    .endVertex();
////                            bufferBuilder
////                                    .vertex((double) currX - x + rainSizeX + 0.5D, (double) maxRainY - y, (double) currZ - z + rainSizeZ + 0.5D)
////                                    .uv(1.0F, (float) minRainY * 0.25F + $$28)
////                                    .color(1.0F, 1.0F, 1.0F, alpha)
////                                    .uv2(lightColor)
////                                    .endVertex();
////                            bufferBuilder
////                                    .vertex((double) currX - x + rainSizeX + 0.5D, (double) minRainY - y, (double) currZ - z + rainSizeZ + 0.5D)
////                                    .uv(1.0F, (float) maxRainY * 0.25F + $$28)
////                                    .color(1.0F, 1.0F, 1.0F, alpha)
////                                    .uv2(lightColor)
////                                    .endVertex();
////                            bufferBuilder
////                                    .vertex((double) currX - x - rainSizeX + 0.5D, (double) minRainY - y, (double) currZ - z - rainSizeZ + 0.5D)
////                                    .uv(0.0F, (float) maxRainY * 0.25F + $$28)
////                                    .color(1.0F, 1.0F, 1.0F, alpha)
////                                    .uv2(lightColor)
////                                    .endVertex();
////                        } else {
////                            // Begin rendering snow
////                            if ($$13 != 1) {
////                                if ($$13 >= 0) {
////                                    tesselator.end();
////                                }
////
////                                $$13 = 1;
////                                RenderSystem.setShaderTexture(0, SNOW_LOCATION);
////                                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
////                            }
////
////                            float $$34 = -((float) (this.ticks & 511) + partialTicks) / 512.0F;
////                            float $$35 = (float) (random.nextDouble() + (double) currTicks * 0.01D * (double) ((float) random.nextGaussian()));
////                            float $$36 = (float) (random.nextDouble() + (double) (currTicks * (float) random.nextGaussian()) * 0.001D);
////                            double $$37 = (double) currX + 0.5D - x;
////                            double $$38 = (double) currZ + 0.5D - z;
////                            float $$39 = (float) Math.sqrt($$37 * $$37 + $$38 * $$38) / (float) rainRenderDistance;
////                            float $$40 = ((1.0F - $$39 * $$39) * 0.3F + 0.5F) * rainLevel;
////                            mutable.set(currX, renderY, currZ);
////                            int $$41 = getLightColor(level, mutable);
////                            int $$42 = $$41 >> 16 & '\uffff';
////                            int $$43 = $$41 & '\uffff';
////                            int $$44 = ($$42 * 3 + 240) / 4;
////                            int $$45 = ($$43 * 3 + 240) / 4;
////                            bufferBuilder.vertex((double) currX - x - rainSizeX + 0.5D, (double) maxRainY - y, (double) currZ - z - rainSizeZ + 0.5D).uv(0.0F + $$35, (float) minRainY * 0.25F + $$34 + $$36).color(1.0F, 1.0F, 1.0F, $$40).uv2($$45, $$44).endVertex();
////                            bufferBuilder.vertex((double) currX - x + rainSizeX + 0.5D, (double) maxRainY - y, (double) currZ - z + rainSizeZ + 0.5D).uv(1.0F + $$35, (float) minRainY * 0.25F + $$34 + $$36).color(1.0F, 1.0F, 1.0F, $$40).uv2($$45, $$44).endVertex();
////                            bufferBuilder.vertex((double) currX - x + rainSizeX + 0.5D, (double) minRainY - y, (double) currZ - z + rainSizeZ + 0.5D).uv(1.0F + $$35, (float) maxRainY * 0.25F + $$34 + $$36).color(1.0F, 1.0F, 1.0F, $$40).uv2($$45, $$44).endVertex();
////                            bufferBuilder.vertex((double) currX - x - rainSizeX + 0.5D, (double) minRainY - y, (double) currZ - z - rainSizeZ + 0.5D).uv(0.0F + $$35, (float) maxRainY * 0.25F + $$34 + $$36).color(1.0F, 1.0F, 1.0F, $$40).uv2($$45, $$44).endVertex();
////                        }
////                    }
////                }
////            }
////
////            if ($$13 >= 0) {
////                tesselator.end();
////            }
////
////            RenderSystem.enableCull();
////            RenderSystem.disableBlend();
////            lightTexture.turnOffLightLayer();
////        }
////    }
//}
