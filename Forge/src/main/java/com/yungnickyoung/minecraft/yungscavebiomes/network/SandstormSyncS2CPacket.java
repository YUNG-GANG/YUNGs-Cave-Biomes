package com.yungnickyoung.minecraft.yungscavebiomes.network;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SandstormSyncS2CPacket {
    private final boolean isActive;
    private final int currSandstormTicks;
    private final long sandstormSeed;
    private final int totalSandstormDurationTicks;

    public SandstormSyncS2CPacket(SandstormServerData sandstormServerData) {
        this.isActive = sandstormServerData.isSandstormActive();
        this.currSandstormTicks = sandstormServerData.getCurrSandstormTicks();
        this.sandstormSeed = sandstormServerData.getSeed();
        this.totalSandstormDurationTicks = sandstormServerData.getTotalSandstormDurationTicks();
    }

    /**
     * Decoder
     */
    public SandstormSyncS2CPacket(FriendlyByteBuf buf) {
        this.isActive = buf.readBoolean();
        this.currSandstormTicks = buf.readInt();
        this.sandstormSeed = buf.readLong();
        this.totalSandstormDurationTicks = buf.readInt();
    }

    /**
     * Encoder
     */
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.isActive);
        buf.writeInt(this.currSandstormTicks);
        buf.writeLong(this.sandstormSeed);
        buf.writeInt(this.totalSandstormDurationTicks);
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

    public int getCurrSandstormTicks() {
        return currSandstormTicks;
    }

    public long getSandstormSeed() {
        return sandstormSeed;
    }

    public int getTotalSandstormDurationTicks() {
        return totalSandstormDurationTicks;
    }
}
