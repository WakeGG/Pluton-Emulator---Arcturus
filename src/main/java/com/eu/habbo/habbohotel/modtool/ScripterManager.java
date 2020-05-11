package com.eu.habbo.habbohotel.modtool;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.util.Map;

public class ScripterManager {
    public static void scripterDetected(GameClient client, String reason) {
        ScripterEvent scripterEvent = new ScripterEvent(client.getHabbo(), reason);
        Emulator.getPluginManager().fireEvent(scripterEvent);

        if (scripterEvent.isCancelled()) return;

        if (Emulator.getConfig().getBoolean("scripter.modtool.tickets", true)) {
            Emulator.getGameEnvironment().getModToolManager().quickTicket(client.getHabbo(), "Scripter", reason);

            Habbo habbo;
            for (Map.Entry<Integer, Habbo> map : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
                habbo = map.getValue();
                if (habbo.getHabboInfo().getRank().getId() >= 6) {
                    THashMap<String, String> keys = new THashMap();
                    keys.put("display", "BUBBLE");
                    keys.put("image", "${image.library.url}notifications/erros.png");
                    keys.put("message", client.getHabbo() + " foi pego no filtro usando SCRIPT\r (" + reason + ")");
                    Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
                }
            }
        }
    }
}
