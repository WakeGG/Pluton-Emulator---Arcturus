package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

public class ConqCommand extends Command {
    public  ConqCommand() {
        super("cmd_disconnect", Emulator.getTexts().getValue("commands.keys.conq").split(";"));
    }
    public boolean handle(GameClient gameClient, String[] params) {
        if (params.length < 2) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.erro.conquista.nome"), RoomChatMessageBubbles.ALERT);
            return true;
        } else if (params.length < 3) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.erro.conquista.codigo"), RoomChatMessageBubbles.ALERT);
            return true;
        } else {
            String targetUsername = params[1];
            String message = "";

            for (int i = 2; i < params.length; ++i) {
                message = message + params[i];
                message = message + " ";
            }


            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(targetUsername);
            if (habbo != null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.sucesso.conquista").replace("%user%", targetUsername), RoomChatMessageBubbles.ALERT);
                AchievementManager.progressAchievement(habbo, Emulator.getGameEnvironment().getAchievementManager().getAchievement(params[2]));
            }
            //else {
              //  gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_alert.user_offline").replace("%user%", targetUsername), RoomChatMessageBubbles.ALERT);
          //  }

            return true;
        }
    }
}
