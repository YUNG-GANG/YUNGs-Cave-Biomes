package com.yungnickyoung.minecraft.yungscavebiomes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class IcicleShatterS2CPacket {
    private final Vec3 pos;

    public IcicleShatterS2CPacket(Vec3 pos) {
        this.pos = pos;
    }

    /**
     * Decoder
     */
    public IcicleShatterS2CPacket(FriendlyByteBuf buf) {
        this.pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    /**
     * Encoder
     */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.pos.x());
        buf.writeDouble(this.pos.y());
        buf.writeDouble(this.pos.z());
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

    public Vec3 getPos() {
        return this.pos;
    }
}
