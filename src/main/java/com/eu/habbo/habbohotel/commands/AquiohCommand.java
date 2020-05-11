package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.habbohotel.gameclients.GameClient;

public class AquiohCommand extends Command {
    public AquiohCommand() {
        super(null, "aquioh".split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            int effect = 1013;
            if (gameClient.getHabbo().getRoomUnit().getEffectId() == 1013)
                effect = 0;

            gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(gameClient.getHabbo(), effect, -1);
            return true;
        }

        return false;
    }
}