package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class BlockMigosCommand extends Command {
    public BlockMigosCommand() {
        super("cmd_blockalert", Emulator.getTexts().getValue("commands.keys.cmd_amigos").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            gameClient.getHabbo().getHabboStats().blockFriendRequests = !gameClient.getHabbo().getHabboStats().blockFriendRequests;
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.succes.cmd_amigos").replace("%state%", (gameClient.getHabbo().getHabboStats().blockFriendRequests ? Emulator.getTexts().getValue("generic.amigos.on") : Emulator.getTexts().getValue("generic.amigos.off"))), RoomChatMessageBubbles.ALERT);

            return true;
        }

        return false;
    }
}
