package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;

public class BlockMencoesCommand extends Command {
    public BlockMencoesCommand() {
        super("cmd_blockalert", Emulator.getTexts().getValue("commands.keys.cmd_mencoes").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            gameClient.getHabbo().getHabboStats().blockMencoesRequests = !gameClient.getHabbo().getHabboStats().blockMencoesRequests;
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.succes.cmd_menncoes").replace("%state%", (gameClient.getHabbo().getHabboStats().blockMencoesRequests ? Emulator.getTexts().getValue("generic.amigos.on") : Emulator.getTexts().getValue("generic.amigos.off"))), RoomChatMessageBubbles.ALERT);

            return true;
        }

        return false;
    }
}
