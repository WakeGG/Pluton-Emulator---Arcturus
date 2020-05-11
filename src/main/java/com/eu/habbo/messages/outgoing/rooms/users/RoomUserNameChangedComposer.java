package com.eu.habbo.messages.outgoing.rooms.users;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;

public class RoomUserNameChangedComposer extends MessageComposer {
    private final Habbo habbo;
    private final boolean includePrefix;
    private final boolean includecornome;
    private final boolean includecornomefim;

    public RoomUserNameChangedComposer(Habbo habbo) {
        this.habbo = habbo;
        this.includePrefix = false;
        this.includecornome = false;
        this.includecornomefim = false;
    }

    public RoomUserNameChangedComposer(Habbo habbo, boolean includePrefix, boolean includecornome, boolean includecornomefim) {
        this.habbo = habbo;
        this.includePrefix = includePrefix;
        this.includecornome = includecornome;
        this.includecornomefim = includecornomefim;
    }

    @Override
    public ServerMessage compose() {
        this.response.init(Outgoing.RoomUserNameChangedComposer);
        this.response.appendInt(this.habbo.getHabboInfo().getId());
        this.response.appendInt(this.habbo.getRoomUnit().getId());
        this.response.appendString((this.includePrefix ? Room.PREFIX_FORMAT.replace("%color%", this.habbo.getHabboInfo().getcorprefixa()).replace("%prefix%", this.habbo.getHabboInfo().getprefix()) : "") + (this.includecornome ? Room.COR_NOME.replace("%color%", this.habbo.getHabboInfo().getcornome()) : "") + this.habbo.getHabboInfo().getUsername() + (this.includecornomefim ? Room.COR_NOME_FIM : ""));
        return this.response;
    }
}