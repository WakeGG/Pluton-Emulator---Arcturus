package com.eu.habbo.plugin.events.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

public class UserCreditsEvent extends UserEvent {

    public int credits;


    public UserCreditsEvent(Habbo habbo, int credits) {
        super(habbo);

        this.credits = credits;

    }
}