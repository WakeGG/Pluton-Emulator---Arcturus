package com.eu.habbo.plugin.events.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

public class UserPointsEvent extends UserEvent {

    public int points;


    public int type;


    public UserPointsEvent(Habbo habbo, int points, int type) {
        super(habbo);

        this.points = points;
        this.type = type;

    }
}