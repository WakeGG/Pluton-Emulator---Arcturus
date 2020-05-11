package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomObject;
import com.eu.habbo.messages.outgoing.rooms.ForwardToRoomComposer;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ColarQCommand extends Command {

    public ColarQCommand() {
        super("cmd_unload", Emulator.getTexts().getValue("commands.keys.cmd_colarquarto").split(";"));
    }

    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        if (params.length == 2) {
            boolean var3 = false;

            int roomId;
            try {
                roomId = Integer.valueOf(params[1]);
            } catch (Exception var33) {
                gameClient.getHabbo().whisper("ID do quarto é inválido", RoomChatMessageBubbles.ALERT);
                return true;
            }

            gameClient.getHabbo().whisper("Carregando quarto...", RoomChatMessageBubbles.ALERT);
            String roomDataJson = "";
            FileReader fileReader = new FileReader(new File("quartos/" + roomId + ".json"));
            Throwable var6 = null;

            try {
                BufferedReader reader = new BufferedReader(fileReader);
                Throwable var8 = null;

                try {
                    String line;
                    try {
                        while((line = reader.readLine()) != null) {
                            roomDataJson = roomDataJson + line;
                        }
                    } catch (Throwable var34) {
                        var8 = var34;
                        throw var34;
                    }
                } finally {
                    if (reader != null) {
                        if (var8 != null) {
                            try {
                                reader.close();
                            } catch (Throwable var32) {
                                var8.addSuppressed(var32);
                            }
                        } else {
                            reader.close();
                        }
                    }

                }
            } catch (Throwable var36) {
                var6 = var36;
                throw var36;
            } finally {
                if (fileReader != null) {
                    if (var6 != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable var31) {
                            var6.addSuppressed(var31);
                        }
                    } else {
                        fileReader.close();
                    }
                }

            }

            Gson gson = new Gson();
            gameClient.getHabbo().whisper("Construindo quarto...", RoomChatMessageBubbles.ALERT);
            RoomObject roomObject = (RoomObject)gson.fromJson(roomDataJson, RoomObject.class);
            int newRoomId = roomObject.insertRoom(gameClient.getHabbo());
            roomObject.insertFurniture();
            if (newRoomId > 0) {
                gameClient.getHabbo().whisper("Quarto clonado (" + newRoomId + ")");
                gameClient.sendResponse(new ForwardToRoomComposer(newRoomId));
            }
        }

        return true;
    }
}
