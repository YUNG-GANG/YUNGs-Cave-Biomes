package com.yungnickyoung.minecraft.yungscavebiomes.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IcicleShatterS2CPacket {
    private BlockPos pos;

    public IcicleShatterS2CPacket(BlockPos pos) {
        this.pos = pos;
    }

    /**
     * Decoder
     */
    public IcicleShatterS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    /**
     * Encoder
     */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    /**
     * Handler
     */
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure this is only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleIcicleShatter(this, ctx))
        );
        ctx.get().setPacketHandled(true);
        return true;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}
