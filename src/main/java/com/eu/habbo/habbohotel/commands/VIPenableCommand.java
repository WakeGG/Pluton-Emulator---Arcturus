package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

public class VIPenableCommand extends Command {
    public VIPenableCommand() {
        super("acc_vip", Emulator.getTexts().getValue("commands.keys.cmd_efeitovip").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            int effect = 630;
            if (gameClient.getHabbo().getRoomUnit().getEffectId() == 630)
                effect = 0;

            gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(gameClient.getHabbo(), effect, -1);
            gameClient.getHabbo().whisper("Efeito ativado com sucesso!", RoomChatMessageBubbles.WIRED);
            return true;
        }

        return false;
    }
}