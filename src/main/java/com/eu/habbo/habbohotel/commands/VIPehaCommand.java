package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomSettingsUpdatedComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VIPehaCommand extends Command {
    public VIPehaCommand() {
        super("acc_vip", Emulator.getTexts().getValue("commands.keys.cmd_ehavip").split(";"));
    }
    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

            if (params.length >= 1) {
                THashMap<String, String> keysa = new THashMap();
                keysa.put("display", "BUBBLE");
                keysa.put("image", "${image.library.url}notifications/vceha.png");
                keysa.put("linkUrl", "event:navigator/goto/" + gameClient.getHabbo().getHabboInfo().getCurrentRoom().getId() + "");
                keysa.put("message", "Um membro VIP convidou você para ir até ao quarto dele, clique aqui para ir");

                Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
                if (room != null) {
                    room.sendComposer((new BubbleAlertComposer("", keysa)).compose());
                    return true;
                }
            }
        return false;
    }
}