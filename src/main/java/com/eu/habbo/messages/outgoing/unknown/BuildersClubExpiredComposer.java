package com.eu.habbo.messages.outgoing.unknown;

import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class BuildersClubExpiredComposer extends MessageComposer {
    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.BuildersClubExpiredComposer);
        this.response.appendInt(999999999); //
        this.response.appendInt(90009); // LIMITE DE MOBIS
        this.response.appendInt(999999999); // TEMPO EM SEGUNDOS
        return this.response;
    }
}
