package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessage;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;
import com.eu.habbo.plugin.EventListener;
import gnu.trove.map.hash.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class VenderQuartoCommand extends Command
{
    public final static HashMap<Integer, Integer> sellingRooms = new HashMap<Integer, Integer>();

        public VenderQuartoCommand() {
            super(null, "venderquarto".split(";"));
        }

        public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        synchronized (sellingRooms)
        {
            Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
            if (room.getOwnerId() == gameClient.getHabbo().getHabboInfo().getId())
            {
                if (sellingRooms.containsKey(room.getId()))
                {
                    sellingRooms.remove(room.getId());
                    int roomId = room.getId();
                    try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
                        try (PreparedStatement statementt = connection.prepareStatement("UPDATE rooms SET is_forsale = '0' WHERE id = '"+roomId+"'")) {
                            statementt.execute();
                        }
                    }
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.removed"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                    return true;
                }
                else if (strings.length >= 2)
                {
                    int points = 0;
                    try
                    {
                        points = Integer.valueOf(strings[1]);
                    }
                    catch (Exception e)
                    {
                        gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.invalid_credits").replace("%currency%", strings[1]), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                        return true;
                    }

                    if (points >= 1)
                    {
                        if (room.hasGuild())
                        {
                            gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.has_guild"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                        }
                        else
                        {
                            gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.confirmed").replace("%currency%", points + ""), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                            sellingRooms.put(room.getId(), points);

                            int roomId = room.getId();
                            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
                                try (PreparedStatement statementt = connection.prepareStatement("UPDATE rooms SET is_forsale = '1' WHERE id = '"+roomId+"'")) {
                                    statementt.execute();
                                }
                            }
                            Collection<Habbo> habbos = new ArrayList<>(room.getHabbos());

                            THashMap<String, String> notify_keys = new THashMap<>();
                            notify_keys.put("display", "BUBBLE");
                            notify_keys.put("image", Emulator.getConfig().getValue("rosie.bubble.image.url"));
                            notify_keys.put("message",
                                    Emulator.getTexts().getValue("rosie.sellroom.bubble.thisroom").replace("%currency%", points+""));
                            ServerMessage bubblemessage = new BubbleAlertComposer("mentioned", notify_keys).compose();

                            for (Habbo habbo : habbos) {
                                GameClient client = habbo.getClient();
                                if (client != null) {
                                    client.sendResponse(bubblemessage);
                                }
                            }
                        }
                        return true;
                    }

                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.invalid_credits").replace("%currency%", points + ""), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                    return true;

                }
                else
                {
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.usage"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                    return true;
                }
            }
            else
            {
                if (sellingRooms.containsKey(room.getId()))
                {
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.forsale").replace("%currency%", sellingRooms.get(room.getId()) + "").replace("%ownername%", room.getOwnerName()), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                }

                else
                {
                    gameClient.sendResponse(new RoomUserWhisperComposer(new RoomChatMessage(Emulator.getTexts().getValue("rosie.sellroom.notforsale"), gameClient.getHabbo(), gameClient.getHabbo(), RoomChatMessageBubbles.ALERT)));
                }
            }

            return true;
        }
    }
}
