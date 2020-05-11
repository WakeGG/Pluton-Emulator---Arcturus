package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.ItemManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.messages.outgoing.rooms.ForwardToRoomComposer;
import com.eu.habbo.messages.outgoing.rooms.HideDoorbellComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUnitOnRollerComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserTalkComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;

public class TrazerCommand extends Command {
    public TrazerCommand() {
        super("cmd_summonrank", Emulator.getTexts().getValue("commands.haibbo.trazer.cmd").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
       // if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() == null)
            //return true;

        if (strings.length == 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(strings[1]);
            if (habbo == null) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("tptome.error.not_found").replace("%user%", strings[1]), RoomChatMessageBubbles.ALERT);
                return true;
            } else if (habbo == gameClient.getHabbo()) {
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.haibbo.trazer.sem"), RoomChatMessageBubbles.ALERT);
                return true;
            } else {
                //Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

               // Emulator.getGameEnvironment().getRoomManager().leaveRoom(habbo, habbo.getHabboInfo().getCurrentRoom());
                //habbo.getClient().sendResponse(new ForwardToRoomComposer(gameClient.getHabbo().getHabboInfo().getCurrentRoom().getId()));

            }

            if (habbo.getHabboInfo().getCurrentRoom() == null) {
                Emulator.getGameEnvironment().getRoomManager().leaveRoom(habbo, habbo.getHabboInfo().getCurrentRoom());
                Emulator.getGameEnvironment().getRoomManager().enterRoom(habbo, gameClient.getHabbo().getHabboInfo().getCurrentRoom().getId(), "", true);
                habbo.getClient().sendResponse(new ForwardToRoomComposer(gameClient.getHabbo().getHabboInfo().getCurrentRoom().getId()));
                habbo.getClient().sendResponse(new HideDoorbellComposer(""));

            }
                if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() == habbo.getHabboInfo().getCurrentRoom()) {
                    RoomTile tile = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getLayout().getTile(gameClient.getHabbo().getRoomUnit().getX(), gameClient.getHabbo().getRoomUnit().getY());
                    if (tile != null && tile.isWalkable()) ;
                    habbo.getHabboInfo().getCurrentRoom().sendComposer(new RoomUnitOnRollerComposer(habbo.getRoomUnit(), tile, habbo.getHabboInfo().getCurrentRoom()).compose());
                    }


                return true;
            }

        return true;
    }
}

