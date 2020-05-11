package com.eu.habbo.messages.outgoing.rooms;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomQueueStatusMessage extends MessageComposer {
    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.RoomQueueStatusMessage);
        {
            this.response.appendInt(1);
            this.response.appendString("visitors");
            this.response.appendInt(2);
            this.response.appendInt(1);
            this.response.appendString("visitors");
            this.response.appendInt(10); // Posicao
            this.response.appendString("spectators");
            this.response.appendInt(1);
            this.response.appendInt(1);
            this.response.appendString("spectators");
            this.response.appendInt(0);

        }
        return this.response;
    }
}