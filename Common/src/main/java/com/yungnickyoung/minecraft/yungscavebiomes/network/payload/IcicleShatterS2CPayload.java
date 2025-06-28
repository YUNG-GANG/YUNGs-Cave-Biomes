package com.yungnickyoung.minecraft.yungscavebiomes.network.payload;

import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record IcicleShatterS2CPayload(double x, double y, double z)
        implements CustomPacketPayload {

    public static final Type<IcicleShatterS2CPayload> TYPE =
            new Type<>(YungsCaveBiomesCommon.id("icicle_shatter"));

    public static final StreamCodec<FriendlyByteBuf, IcicleShatterS2CPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, IcicleShatterS2CPayload::x,
            ByteBufCodecs.DOUBLE, IcicleShatterS2CPayload::y,
            ByteBufCodecs.DOUBLE, IcicleShatterS2CPayload::z,
            IcicleShatterS2CPayload::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
