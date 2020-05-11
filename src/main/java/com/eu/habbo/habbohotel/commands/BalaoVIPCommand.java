package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.users.MeMenuSettingsComposer;

public class BalaoVIPCommand extends Command {
    public BalaoVIPCommand() {
        super("acc_vip", Emulator.getTexts().getValue("commands.keys.cmd_balaovip").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        if (params.length >= 1) {

            gameClient.getHabbo().getHabboStats().chatColor = RoomChatMessageBubbles.getBubble(21);
            gameClient.sendResponse(new MeMenuSettingsComposer(gameClient.getHabbo()));
            gameClient.getHabbo().whisper("O balão de fala VIP foi ativado", RoomChatMessageBubbles.ALERT);
            return true;
        } else {
            gameClient.getHabbo().getHabboStats().chatColor = RoomChatMessageBubbles.NORMAL;
            gameClient.getHabbo().whisper("O balão de fala VIP foi desativado", RoomChatMessageBubbles.ALERT);
            return true;
        }
    }
}
