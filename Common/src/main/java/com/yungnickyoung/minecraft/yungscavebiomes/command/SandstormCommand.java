package com.yungnickyoung.minecraft.yungscavebiomes.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;

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
            commandSource.sendSuccess(new TextComponent("Sandstorm started in all Lost Caves."), false);
        } else if (action.equals("stop")) {
            commandSource.sendSuccess(new TextComponent("Sandstorm stopped in all Lost Caves."), false);
        } else {
            commandSource.sendFailure(new TextComponent("Unrecognized action."));
            return -1;
        }
        return 1;
    }
}
