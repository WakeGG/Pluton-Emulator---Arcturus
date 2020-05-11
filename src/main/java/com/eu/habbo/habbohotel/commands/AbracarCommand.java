package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

public class AbracarCommand extends Command {
    public AbracarCommand() {
        super("cmd_push", Emulator.getTexts().getValue("commands.keys.cmd_abracar").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        if (params.length >= 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);

            if (habbo == null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_push.not_found").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                return true;
            } else if (habbo == gameClient.getHabbo()) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_geral"), RoomChatMessageBubbles.ALERT);
                return true;
            } else {

                if (habbo != gameClient.getHabbo()) {
                    if (habbo.getRoomUnit().getCurrentLocation().distance(gameClient.getHabbo().getRoomUnit().getCurrentLocation()) <= 1.5) {
                        habbo.getRoomUnit().lookAtPoint(gameClient.getHabbo().getRoomUnit().getCurrentLocation());
                        gameClient.getHabbo().getRoomUnit().lookAtPoint(habbo.getRoomUnit().getCurrentLocation());
                        gameClient.getHabbo().talk(Emulator.getTexts().getValue("comando.abracar.mensagemum").replace("%beijador%", gameClient.getHabbo().getHabboInfo().getUsername()).replace("%userbeijado%", habbo.getHabboInfo().getUsername()), RoomChatMessageBubbles.HEARTS);
                        habbo.talk(Emulator.getTexts().getValue("comando.abracar.mensagemdois").replace("%beijador%", gameClient.getHabbo().getHabboInfo().getUsername()).replace("%userbeijado%", habbo.getHabboInfo().getUsername()), RoomChatMessageBubbles.HEARTS);

                    } else {
                        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("comando.abracar.mensagemlonge").replace("%userbeijado%", habbo.getHabboInfo().getUsername()));
                    }
                }
            }

        }
        return true;
    }
}