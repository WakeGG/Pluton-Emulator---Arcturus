package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

import java.util.Map;

public class OnlinesCommand extends Command {
    public OnlinesCommand() {
        super(null, "onlines".split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            gameClient.getHabbo().whisper("Estão neste momento: \r" +
                            "" + Emulator.getGameEnvironment().getHabboManager().getOnlineCount() +" usuários online \r"
                    , RoomChatMessageBubbles.WIRED);
        }

        return true;
    }
}

