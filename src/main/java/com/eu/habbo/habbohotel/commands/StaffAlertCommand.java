package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.messenger.Message;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.friends.FriendChatMessageComposer;

import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class StaffAlertCommand extends Command {
    public StaffAlertCommand() {
        super("cmd_staffalert", Emulator.getTexts().getValue("commands.keys.cmd_staffalert").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length > 1) {
            StringBuilder message = new StringBuilder();
            for (int i = 1; i < params.length; i++) {
                message.append(params[i]).append(" ");
            }


            //Emulator.getRconServer().addRCONMessage("eha", HotelAlert.class);
            //Emulator.getRconServer().addRCONMessage(new HotelAlert("{\"message\":\"" + message + "\", \"url\":\"" + gameClient.getHabbo().getHabboInfo().getUsername() + "\"}"));


                for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                    Habbo habbo = set.getValue();

                    if (habbo.getHabboInfo().getRank().getId() >= 6) {
                    habbo.whisper("[STAFF] " + gameClient.getHabbo().getHabboInfo().getUsername() + ": " + message + "", RoomChatMessageBubbles.ALERT);
                }
            }

            //for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
    //Habbo habbo = set.getValue();
               // if (habbo.getHabboInfo().getRank().getId() >= 6) {
              //      gameClient.getHabbo().whisper("[STAFF] <" + gameClient.getHabbo().getHabboInfo().getUsername() + "> : " + message, RoomChatMessageBubbles.WIRED);
             //   }
 // }

            //Emulator.getGameEnvironment().getHabboManager().staffAlert(message + "\r\n-" + gameClient.getHabbo().getHabboInfo().getUsername());
           // Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new FriendChatMessageComposer(new Message(gameClient.getHabbo().getHabboInfo().getId(), -1, message.toString())).compose(), "acc_staff_chat", gameClient);
        } else {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_staffalert.forgot_message"), RoomChatMessageBubbles.ALERT);
        }

        return true;
    }
}
