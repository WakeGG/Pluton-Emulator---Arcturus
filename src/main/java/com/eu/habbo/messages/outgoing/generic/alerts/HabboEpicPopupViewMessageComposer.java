package com.eu.habbo.messages.outgoing.generic.alerts;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import gnu.trove.map.hash.THashMap;

import java.util.Map;


public class HabboEpicPopupViewMessageComposer extends MessageComposer {
    private String image;

    public HabboEpicPopupViewMessageComposer(String image) {
        this.image = image;
    }

    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.HabboEpicPopupViewMessageComposer);
        this.response.appendString("${image.library.url}" + image + ".png");;

        return this.response;
    }
}
