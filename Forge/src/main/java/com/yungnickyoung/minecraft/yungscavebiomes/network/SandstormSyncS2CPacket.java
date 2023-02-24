package com.yungnickyoung.minecraft.yungscavebiomes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SandstormSyncS2CPacket {
    private boolean isActive;
    private int sandstormTime;
    private long sandstormSeed;
    private int totalSandstormDuration;

    public SandstormSyncS2CPacket(boolean isActive, int sandstormTime, long sandstormSeed, int totalSandstormDuration) {
        this.isActive = isActive;
        this.sandstormTime = sandstormTime;
        this.sandstormSeed = sandstormSeed;
        this.totalSandstormDuration = totalSandstormDuration;
    }

    /**
     * Decoder
     */
    public SandstormSyncS2CPacket(FriendlyByteBuf buf) {
        this.isActive = buf.readBoolean();
        this.sandstormTime = buf.readInt();
        this.sandstormSeed = buf.readLong();
        this.totalSandstormDuration = buf.readInt();
    }

    /**
     * Encoder
     */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isActive);
        buf.writeInt(this.sandstormTime);
        buf.writeLong(this.sandstormSeed);
        buf.writeInt(this.totalSandstormDuration);
    }

    /**
     * Handler
     */
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                // Make sure this is only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleSandstormSync(this, ctx))
        );
        ctx.get().setPacketHandled(true);
        return true;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getSandstormTime() {
        return sandstormTime;
    }

    public long getSandstormSeed() {
        return sandstormSeed;
    }

    public int getTotalSandstormDuration() {
        return totalSandstormDuration;
    }
}
