package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.extras.lajota;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.furniture.FurnitureMovedEvent;
import com.eu.habbo.plugin.events.furniture.FurniturePlacedEvent;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;


public class LajotaCommand extends Command implements EventListener {
    public static String BUILD_HEIGHT_KEY = "be.build_height";

    public LajotaCommand() {
        super(null, "lajota".split(";"));


    }


    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length == 2) {
            Double height = -1.0;

            try {
                height = Double.valueOf(strings[1]);
            } catch (Exception e) {
            }

            if (height > 80 || height < 0) {
                gameClient.getHabbo().whisper("Altura inválida", RoomChatMessageBubbles.WIRED);
                return true;
            }

            gameClient.getHabbo().getHabboStats().cache.put(BUILD_HEIGHT_KEY, height);
            gameClient.getHabbo().whisper("Altura definida:" + height, RoomChatMessageBubbles.WIRED);
        } else {
            if (gameClient.getHabbo().getHabboStats().cache.containsKey(BUILD_HEIGHT_KEY)) {
                gameClient.getHabbo().getHabboStats().cache.remove(BUILD_HEIGHT_KEY);
                gameClient.getHabbo().whisper("Lajota está desativada", RoomChatMessageBubbles.WIRED);
                return true;
            }
            gameClient.getHabbo().whisper("Altura não especificada", RoomChatMessageBubbles.WIRED);
        }
        return true;
    }



    @EventHandler
    public static void onUserExitRoomEvent(UserExitRoomEvent event)
    {
        event.habbo.getHabboStats().cache.remove(BUILD_HEIGHT_KEY);
    }


    @EventHandler
    public static void onFurniturePlaced(final FurniturePlacedEvent event)
    {
        if (event.location != null)
        {
            if (event.habbo.getHabboStats().cache.containsKey(BUILD_HEIGHT_KEY))
            {
                Emulator.getThreading().run(new Runnable() {
                    public void run() {
                        event.furniture.setZ((Double) event.habbo.getHabboStats().cache.get(BUILD_HEIGHT_KEY));
                        event.habbo.getHabboInfo().getCurrentRoom().updateItem(event.furniture);
                        event.furniture.needsUpdate(true);
                    }
                }, 25);
            }
        }
    }

    @EventHandler
    public static void onFurnitureMoved(final FurnitureMovedEvent event)
    {
        if (event.newPosition != null)
        {
            if (event.habbo.getHabboStats().cache.containsKey(BUILD_HEIGHT_KEY))
            {
                Emulator.getThreading().run(new Runnable() {
                    public void run() {
                        event.furniture.setZ((Double) event.habbo.getHabboStats().cache.get(BUILD_HEIGHT_KEY));
                        event.habbo.getHabboInfo().getCurrentRoom().updateItem(event.furniture);
                        event.furniture.needsUpdate(true);
                    }
                }, 25);
            }
        }
    }
}
