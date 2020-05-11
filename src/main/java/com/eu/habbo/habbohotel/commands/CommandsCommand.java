package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;

import java.util.List;

public class CommandsCommand extends Command {
    public CommandsCommand() {
        super("cmd_commands", Emulator.getTexts().getValue("commands.keys.cmd_commands").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 1) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/user.php"));
        }
        //if (gameClient.getHabbo().getClient().getHabbo().hasPermission(Permission.ACC_VIP)) {
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 2) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/vip.php"));
        }
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 3) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/embaixador.php"));
        }
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 4) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/embaixador.php"));
        }
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 5) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/embaixador.php"));
        }
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 6) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/mod.php"));
        }
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() >= 7) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/staff.php"));
        }
        if (gameClient.getHabbo().getHabboInfo().getRank().getId() == 11) {
            gameClient.getHabbo().getClient().sendResponse(new NuxAlertComposer("habbopages/comandos/dev.php"));
        }



       // StringBuilder message = new StringBuilder("Your Commands");
      //  List<Command> commands = Emulator.getGameEnvironment().getCommandHandler().getCommandsForRank(gameClient.getHabbo().getHabboInfo().getRank().getId());
       // message.append("(").append(commands.size()).append("):\r\n");

      //  for (Command c : commands) {
       //     message.append(Emulator.getTexts().getValue("commands.description." + c.permission, "commands.description." + c.permission)).append("\r");
       // }

        //gameClient.getHabbo().alert(new String[]{message.toString()});

        return true;
    }
}
