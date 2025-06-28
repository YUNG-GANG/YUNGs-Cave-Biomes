package com.yungnickyoung.minecraft.yungscavebiomes.network.payload;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record SandstormSyncS2CPayload(boolean isActive, int sandstormTime, long sandstormSeed, int totalSandstormDuration)
        implements CustomPacketPayload {

    public static final Type<SandstormSyncS2CPayload> TYPE =
            new Type<>(YungsCaveBiomesCommon.id("sandstorm_sync"));

    public static final StreamCodec<FriendlyByteBuf, SandstormSyncS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SandstormSyncS2CPayload::isActive,
            ByteBufCodecs.INT, SandstormSyncS2CPayload::sandstormTime,
            ByteBufCodecs.VAR_LONG, SandstormSyncS2CPayload::sandstormSeed,
            ByteBufCodecs.INT, SandstormSyncS2CPayload::totalSandstormDuration,
            SandstormSyncS2CPayload::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
