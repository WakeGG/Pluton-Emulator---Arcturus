package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import com.eu.habbo.messages.outgoing.rooms.ForwardToRoomComposer;
import com.google.gson.Gson;
import com.eu.habbo.habbohotel.rooms.RoomObject;
import gnu.trove.map.hash.THashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CopiarQCommand extends Command {

    public CopiarQCommand() {
        super("cmd_unload", Emulator.getTexts().getValue("commands.keys.cmd_copiarquarto").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        if (params.length >= 0) {
            if (params[1].equalsIgnoreCase("copiar")) {


                Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
                if (room != null) {
                    gameClient.getHabbo().whisper("Copiando quarto " + room.getName(), RoomChatMessageBubbles.ALERT);
                    RoomObject roomObject = new RoomObject(room);
                    Gson gson = new Gson();
                    String roomObjectJson = gson.toJson(roomObject);
                    File file = new File("quartos/" + room.getId() + ".json");
                    file.getParentFile().mkdirs();

                    try {
                        FileWriter fw = new FileWriter(file);
                        Throwable var9 = null;

                        try {
                            fw.write(roomObjectJson);
                        } catch (Throwable var19) {
                            var9 = var19;
                            throw var19;
                        } finally {
                            if (fw != null) {
                                if (var9 != null) {
                                    try {
                                        fw.close();
                                    } catch (Throwable var18) {
                                        var9.addSuppressed(var18);
                                    }
                                } else {
                                    fw.close();
                                }
                            }

                        }
                    } catch (Exception var21) {
                        gameClient.getHabbo().alert("Algo deu errado:" + var21.getMessage());
                        return true;
                    }
                    gameClient.getHabbo().whisper("Quarto copiado (" + room.getId() + ")", RoomChatMessageBubbles.ALERT);
                }

            }
        }
        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("usar")) {
                    boolean var3 = false;

                    int roomId;
                    try {
                        roomId = Integer.valueOf(params[2]);
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
            }
        return true;
    }
}