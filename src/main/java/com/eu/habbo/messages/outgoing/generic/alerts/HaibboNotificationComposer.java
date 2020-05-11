package com.eu.habbo.messages.outgoing.generic.alerts;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class HaibboNotificationComposer extends MessageComposer {
    public static final int STARS_NOT_ENOUGH_USERS = 5;

    private final int type;

    public HaibboNotificationComposer(int type) {
        this.type = type;
    }

    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.CustomNotificationComposer);
        this.response.appendInt(this.STARS_NOT_ENOUGH_USERS);
        return this.response;
    }
}
