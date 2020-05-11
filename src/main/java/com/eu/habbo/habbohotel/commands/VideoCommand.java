package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomSettingsUpdatedComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VideoCommand extends Command {
    public VideoCommand() {
        super("cmd_about", Emulator.getTexts().getValue("commands.keys.cmd_video").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        String url = "";
        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        if (room != null) {
            if (params.length >= 2) {
                StringBuilder message = new StringBuilder();

                for (int i = 1; i < params.length; ++i) {
                    url = url + params[i];
                    url = url + " ";
                }

                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
                     PreparedStatement pstmt = connection.prepareStatement("INSERT INTO socket_engagements (action, hash, string_1) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
                     {
                        pstmt.setString(1, "roomvideo");
                        pstmt.setString(2, "all");
                        pstmt.setInt(3, room.getId());
                        pstmt.execute();
                     } catch (SQLException e) {
                    Emulator.getLogging().logSQLException(e);
                }
            }
        }
        return false;
    }
}
