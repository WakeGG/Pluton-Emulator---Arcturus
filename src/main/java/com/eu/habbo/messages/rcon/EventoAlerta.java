package com.eu.habbo.messages.rcon;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.google.gson.Gson;
import gnu.trove.map.hash.THashMap;

import java.util.Map;

public class EventoAlerta extends RCONMessage<EventoAlerta.JSON> {
    public EventoAlerta() {
        super(EventoAlerta.JSON.class);
    }

    @Override
    public void handle(Gson gson, JSON json) {
        THashMap<String, String> keys = new THashMap<>();

        if (!json.nomedoquarto.isEmpty()) {
            keys.put("nomedoquarto", json.nomedoquarto);
        }

        if (!json.idquarto.isEmpty()) {
            keys.put("idquarto", json.idquarto);
        }

        if (!json.nomemod.isEmpty()) {
            keys.put("nomemod", json.nomemod);
        }

        if (!json.imagem.isEmpty()) {
            keys.put("imagem", json.imagem);
        }

        ServerMessage message = new BubbleAlertComposer(json.bubble_key, keys).compose();
        for (Map.Entry<Integer, Habbo> set : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
            Habbo habbo = set.getValue();
            if (habbo.getHabboStats().blockStaffAlerts)
                continue;

            habbo.getClient().sendResponse(message);
        }
    }

    static class JSON {

        public String bubble_key = "";


        public String nomedoquarto = "";


        public String idquarto = "";


        public String nomemod = "";


        public String imagem = "";
    }
}