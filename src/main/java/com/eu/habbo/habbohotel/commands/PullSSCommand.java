package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserTalkComposer;

public class PullSSCommand extends Command {
    public PullSSCommand() {
        super("cmd_vip", Emulator.getTexts().getValue("commands.keys.cmd_pulls").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length == 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);

            if (habbo == null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.not_found").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                return true;
            } else if (habbo == gameClient.getHabbo()) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.pull_self"), RoomChatMessageBubbles.ALERT);
                return true;
            } else {
                int distanceX = habbo.getRoomUnit().getX() - gameClient.getHabbo().getRoomUnit().getX();
                int distanceY = habbo.getRoomUnit().getY() - gameClient.getHabbo().getRoomUnit().getY();

                if (distanceX < -6 || distanceX > 6 || distanceY < -6 || distanceY > 6) {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.cant_reach").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                    return true;
                } else {
                    RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTileInFront(gameClient.getHabbo().getRoomUnit().getCurrentLocation(), gameClient.getHabbo().getRoomUnit().getBodyRotation().getValue());

                    if (tile != null && tile.isWalkable()) {
                        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getDoorTile() == tile) {
                            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_pull.invalid").replace("%username%", params[1]));
                            return true;
                        }
                        habbo.getRoomUnit().setGoalLocation(tile);
                        gameClient.getHabbo().getHabboInfo().getCurrentRoom().sendComposer(new RoomUserTalkComposer(new RoomChatMessage(Emulator.getTexts().getValue("commands.succes.cmd_pull.pull").replace("%user%", params[1]).replace("%gender_name%", (gameClient.getHabbo().getHabboInfo().getGender().equals(HabboGender.M) ? Emulator.getTexts().getValue("gender.him") : Emulator.getTexts().getValue("gender.her"))), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.NORMAL)).compose());
                    }
                }
            }
            return true;
        }

        return true;
    }
}
