package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;

import java.util.Map;

public class VIPAlertCommand extends Command {
    public VIPAlertCommand() {
        super("acc_vip", Emulator.getTexts().getValue("commands.keys.cmd_vipalerta_desativado").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length > 1) {
            StringBuilder message = new StringBuilder();
            for (int i = 1; i < params.length; i++) {
                message.append(params[i]).append(" ");
            }


            for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                Habbo habbo = set.getValue();
                if (habbo.getHabboInfo().getRank().getId() >= 2 && habbo.getHabboStats().blockStaffAlerts)
                    continue;

                //habbo.getClient().sendResponse(msg);
               // Void msg = habbo.whisper("[VIP] " + gameClient.getHabbo().getHabboInfo().getUsername() + ": " + message + "", RoomChatMessageBubbles.ALERT);

            }

                for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                    Habbo habbo = set.getValue();

                    if (habbo.getHabboInfo().getRank().getId() >= 2 || habbo.getHabboStats().blockCVRequests) {
                        }
            }

            } else {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_staffalert.forgot_message"), RoomChatMessageBubbles.ALERT);
        }

        return true;
    }
}
