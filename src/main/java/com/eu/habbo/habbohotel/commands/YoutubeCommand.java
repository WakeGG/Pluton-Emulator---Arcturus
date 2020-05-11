package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomSettingsUpdatedComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.*;
import java.util.Map;

public class YoutubeCommand extends Command {
    public YoutubeCommand() {
        super(null, new String[]{"egergergergergergergerg234234234234244", "234234234234236fgfgdwd3434wwww"});
    }
    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom().getOwnerId() == gameClient.getHabbo().getHabboInfo().getId() || gameClient.getHabbo().getHabboInfo().getRank().getId() > 6) {
            String tipo = params[1];
            if (params.length < 2) {
                for (int i = 2; i < params.length; ++i) {
                    tipo = tipo.replace(";", "").replace("https://youtube.com/watch?v=", "").replace("https://music.youtube.com/watch?v=", "") + params[i];
                    tipo = tipo.replace(";", "").replace("https://youtube.com/watch?v=", "").replace("https://music.youtube.com/watch?v=", "");
                }


            if (tipo.length() == 0) {
                gameClient.getHabbo().whisper("Insira o video que quer ver", RoomChatMessageBubbles.ALERT);
                return true;
            }

                for (Habbo habbo : gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbos()) {

                    habbo.alert(tipo + "\r\n    -" + gameClient.getHabbo().getHabboInfo().getUsername());

                    //Emulator.getGameEnvironment().getHabboManager().novoevento(habbo.getHabboInfo().getId(),tipo);
                    try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
                         PreparedStatement pstmt = connection.prepareStatement("INSERT INTO socket_engagements (action, hash, label) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, "roomvideo");
                        pstmt.setInt(2, habbo.getHabboInfo().getId());
                        pstmt.setString(3, tipo);
                        pstmt.execute();
                    } catch (SQLException e) {
                        Emulator.getLogging().logSQLException(e);
                    }
                }
            }
        }
        return false;
    }
}
