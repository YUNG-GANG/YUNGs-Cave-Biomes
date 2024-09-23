package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCommand;
import com.yungnickyoung.minecraft.yungscavebiomes.YungsCaveBiomesCommon;
import com.yungnickyoung.minecraft.yungscavebiomes.command.SandstormCommand;

@AutoRegister(YungsCaveBiomesCommon.MOD_ID)
public class CommandModule {
    @AutoRegister("sandstorm")
    public static final AutoRegisterCommand SANDSTORM_COMMAND = AutoRegisterCommand.of(SandstormCommand::register);
}
