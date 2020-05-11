package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.events.calendar.AdventCalendarDataComposer;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PublicosCommand extends Command {
    public PublicosCommand() {
        super("cmd_calendar", Emulator.getTexts().getValue("commands.keys.cmd_publicos").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        int roomId = 0;
        if (params.length == 2) {
            try {
                roomId = Integer.valueOf(params[1]);
                if (roomId <= 0) {
                    gameClient.getHabbo().whisper("O id do quarto deve ser maior que 0", RoomChatMessageBubbles.ALERT);
                    return true;
                }
            } catch (Exception var37) {
                gameClient.getHabbo().whisper("Indique o id do quarto", RoomChatMessageBubbles.ALERT);
                return true;
            }
        }

        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(roomId);
        if (room == null) {
            gameClient.getHabbo().whisper("Quarto com o id " + roomId + " não foi encontrado", RoomChatMessageBubbles.ALERT);
            return true;
        } else {
            boolean status = !room.isPublicRoom();
            room.setPublicRoom(status);

            try {
                Connection connection = Emulator.getDatabase().getDataSource().getConnection();
                Throwable var7 = null;

                try {
                    PreparedStatement statement = connection.prepareStatement("UPDATE rooms SET is_public = ? WHERE id = ?");
                    Throwable var9 = null;

                    try {
                        statement.setString(1, status ? "1" : "0");
                        statement.setInt(2, roomId);
                        statement.executeUpdate();
                    } catch (Throwable var36) {
                        var9 = var36;
                        throw var36;
                    } finally {
                        if (statement != null) {
                            if (var9 != null) {
                                try {
                                    statement.close();
                                } catch (Throwable var35) {
                                    var9.addSuppressed(var35);
                                }
                            } else {
                                statement.close();
                            }
                        }

                    }
                } catch (Throwable var39) {
                    var7 = var39;
                    throw var39;
                } finally {
                    if (connection != null) {
                        if (var7 != null) {
                            try {
                                connection.close();
                            } catch (Throwable var34) {
                                var7.addSuppressed(var34);
                            }
                        } else {
                            connection.close();
                        }
                    }

                }
            } catch (SQLException var41) {
                Emulator.getLogging().logSQLException(var41);
            }

            gameClient.getHabbo().whisper("O quarto com o id " + roomId + " foi colocado nos públicos", RoomChatMessageBubbles.ALERT);
            return true;
        }
    }
}
