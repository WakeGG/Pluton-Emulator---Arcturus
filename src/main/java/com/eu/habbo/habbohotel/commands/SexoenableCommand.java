package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class SexoenableCommand extends Command {
    public SexoenableCommand() {
        super("acc_vip", Emulator.getTexts().getValue("commands.keys.cmd_efeitosexo").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            int effect = 3100;
            if (gameClient.getHabbo().getRoomUnit().getEffectId() == 3100)
                effect = 0;

            gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(gameClient.getHabbo(), effect, -1);
            gameClient.getHabbo().whisper("Foda com tudo!", RoomChatMessageBubbles.WIRED);
            return true;
        }

        return false;
    }
}