package com.yungnickyoung.minecraft.yungscavebiomes.command;

import com.mojang.brigadier.CommandDispatcher;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.Permissions;

public class SandstormCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ctx, Commands.CommandSelection selection) {
        dispatcher.register(Commands.literal("sandstorm")
                .requires((source) -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                .then(Commands.literal("start")
                        .executes(context -> execute(context.getSource(), "start")))
                .then(Commands.literal("stop")
                        .executes(context -> execute(context.getSource(), "stop")))
        );
    }

    public static int execute(CommandSourceStack commandSource, String action) {
        var level = commandSource.getLevel();
        if (action.equals("start")) {
            SandstormServerData sandstormServerData = ((ISandstormServerDataProvider) level).getSandstormServerData();
            sandstormServerData.start(level);
            commandSource.sendSuccess(() -> Component.translatable("command.sandstorm.start", level.dimension().identifier().toString()), false);
            return 1;
        } else if (action.equals("stop")) {
            SandstormServerData sandstorm = ((ISandstormServerDataProvider) level).getSandstormServerData();
            sandstorm.stop(level);
            commandSource.sendSuccess(() -> Component.translatable("command.sandstorm.stop", level.dimension().identifier().toString()), false);
            return 1;
        } else {
            commandSource.sendFailure(Component.literal("Unrecognized action."));
            return -1;
        }
    }
}
