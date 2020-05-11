package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.CustomNotificationComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InteractionHabboClubTeleportTile extends InteractionTeleportTile {
    public InteractionHabboClubTeleportTile(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public InteractionHabboClubTeleportTile(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {

        Habbo habbo = room.getHabbo(roomUnit);
        return habbo != null && habbo.getHabboStats().viphaibboa();
    }

    @Override
    public void onWalkOn(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOn(roomUnit, room, objects);

        if (this.canWalkOn(roomUnit, room, objects)) {
            this.setExtradata("1");
            room.updateItemState(this);
        } else {
            Habbo habbo = room.getHabbo(roomUnit);

            if (habbo != null) {
                habbo.getClient().sendResponse(new CustomNotificationComposer(CustomNotificationComposer.GATE_NO_HC));
            }

        }
    }

    @Override
    public boolean canUseTeleport(GameClient client, Room room) {
        return super.canUseTeleport(client, room) && client.getHabbo().getHabboStats().viphaibboa();
    }
}