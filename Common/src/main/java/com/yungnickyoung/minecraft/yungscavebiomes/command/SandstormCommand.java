package com.yungnickyoung.minecraft.yungscavebiomes.command;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SandstormCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("sandstorm")
                .requires((source) -> source.hasPermission(2))
                .then(Commands.literal("start")
                        .executes(context -> execute(context.getSource(), "start")))
                .then(Commands.literal("stop")
                        .executes(context -> execute(context.getSource(), "stop")))
        );
    }

    public static int execute(CommandSourceStack commandSource, String action) {
        if (action.equals("start")) {
            SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) commandSource.getLevel()).getSandstormServerData();
            sandstormServerData.start();
            commandSource.sendSuccess(new TranslatableComponent("command.sandstorm.start", commandSource.getLevel().dimension().location().toString()), false);
            return 1;
        } else if (action.equals("stop")) {
            SandstormServerData sandstorm = ((ISandstormServerDataProvider) commandSource.getLevel()).getSandstormServerData();
            sandstorm.stop();
            commandSource.sendSuccess(new TranslatableComponent("command.sandstorm.stop", commandSource.getLevel().dimension().location().toString()), false);
            return 1;
        } else {
            commandSource.sendFailure(new TextComponent("Unrecognized action."));
            return -1;
        }
    }
}
