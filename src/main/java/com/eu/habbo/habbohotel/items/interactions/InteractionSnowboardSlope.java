package com.eu.habbo.habbohotel.items.interactions;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.Achievement;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.*;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.habbohotel.wired.WiredEffectType;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserStatusComposer;
import gnu.trove.set.hash.THashSet;
import org.apache.commons.math3.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InteractionSnowboardSlope extends HabboItem {
    public InteractionSnowboardSlope(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    public InteractionSnowboardSlope(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    @Override
    public void serializeExtradata(ServerMessage serverMessage) {
        serverMessage.appendInt((this.isLimited() ? 256 : 0));
        serverMessage.appendString(this.getExtradata());

        super.serializeExtradata(serverMessage);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects) {
        return true;
    }

    @Override
    public boolean isWalkable() {
        return this.getBaseItem().allowWalk();
    }

    @Override
    public void onClick(GameClient client, Room room, Object[] objects) throws Exception {
        super.onClick(client, room, objects);

        if (client != null) {
            if (!room.hasRights(client.getHabbo()) && !(objects.length >= 2 && objects[1] instanceof WiredEffectType && objects[1] == WiredEffectType.TOGGLE_STATE))
                return;
        }

        if (objects.length > 0) {
            if (objects[0] instanceof Integer && room != null) {
                HabboItem topItem = room.getTopItemAt(this.getX(), this.getY());
                if (topItem != null && !topItem.equals(this)) { // multiheight items cannot change height even if there is a stackable item on top - no items allowed on top
                    return;
                }

                this.needsUpdate(true);

                if (this.getExtradata().length() == 0)
                    this.setExtradata("0");

                if (this.getBaseItem().getMultiHeights().length > 0) {
                    this.setExtradata("" + (Integer.valueOf(this.getExtradata()) + 1) % (this.getBaseItem().getMultiHeights().length));
                    this.needsUpdate(true);
                    room.updateTiles(room.getLayout().getTilesAt(room.getLayout().getTile(this.getX(), this.getY()), this.getBaseItem().getWidth(), this.getBaseItem().getLength(), this.getRotation()));
                    room.updateItemState(this);
                    //room.sendComposer(new UpdateStackHeightComposer(this.getX(), this.getY(), this.getBaseItem().getMultiHeights()[Integer.valueOf(this.getExtradata())] * 256.0D).compose());
                }

                if (this.isWalkable()) {
                    THashSet<Habbo> habbos = room.getHabbosOnItem(this);
                    THashSet<RoomUnit> updatedUnits = new THashSet<>();
                    for (Habbo habbo : habbos) {
                        if (habbo.getRoomUnit() == null)
                            continue;

                        if (habbo.getRoomUnit().hasStatus(RoomUnitStatus.MOVE))
                            continue;

                        if (this.getBaseItem().getMultiHeights().length >= 0) {
                            if (this.getBaseItem().allowSit()) {
                                habbo.getRoomUnit().setStatus(RoomUnitStatus.SIT, this.getBaseItem().getMultiHeights()[(this.getExtradata().isEmpty() ? 0 : Integer.valueOf(this.getExtradata()) % (this.getBaseItem().getMultiHeights().length))] * 1.0D + "");
                            } else {
                                habbo.getRoomUnit().setZ(habbo.getRoomUnit().getCurrentLocation().getStackHeight());
                                habbo.getRoomUnit().setPreviousLocationZ(habbo.getRoomUnit().getZ());
                            }
                        }

                        updatedUnits.add(habbo.getRoomUnit());
                    }
                    room.sendComposer(new RoomUserStatusComposer(updatedUnits, true).compose());
                }
            }
        }
    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) {
    }

    @Override
    public void onWalkOn(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOn(roomUnit, room, objects);

        if (roomUnit != null) {
            if (this.getBaseItem().getEffectF() > 0 || this.getBaseItem().getEffectM() > 0) {
                if (roomUnit.getRoomUnitType().equals(RoomUnitType.USER)) {
                    Habbo habbo = room.getHabbo(roomUnit);

                    if (habbo != null) {
                        if (habbo.getHabboInfo().getGender().equals(HabboGender.M) && this.getBaseItem().getEffectM() > 0 && habbo.getRoomUnit().getEffectId() != this.getBaseItem().getEffectM()) {
                            room.giveEffect(habbo, this.getBaseItem().getEffectM(), -1);
                            return;
                        }

                        if (habbo.getHabboInfo().getGender().equals(HabboGender.F) && this.getBaseItem().getEffectF() > 0 && habbo.getRoomUnit().getEffectId() != this.getBaseItem().getEffectF()) {
                            room.giveEffect(habbo, this.getBaseItem().getEffectF(), -1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onWalkOff(RoomUnit roomUnit, Room room, Object[] objects) throws Exception {
        super.onWalkOff(roomUnit, room, objects);

        if (roomUnit != null) {
            if (this.getBaseItem().getEffectF() > 0 || this.getBaseItem().getEffectM() > 0) {
                if (roomUnit.getRoomUnitType().equals(RoomUnitType.USER)) {
                    Habbo habbo = room.getHabbo(roomUnit);

                    if (habbo != null) {
                        if (habbo.getHabboInfo().getGender().equals(HabboGender.M) && this.getBaseItem().getEffectM() > 0) {
                            room.giveEffect(habbo, 0, -1);
                            return;
                        }

                        if (habbo.getHabboInfo().getGender().equals(HabboGender.F) && this.getBaseItem().getEffectF() > 0) {
                            room.giveEffect(habbo, 0, -1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onPlace(Room room) {
        Achievement snooow = Emulator.getGameEnvironment().getAchievementManager().getAchievement("snowBoardBuild");

        int snooowProgress = 0;
        Habbo owner = room.getHabbo(this.getUserId());

        if (owner == null) {
            snooowProgress = AchievementManager.getAchievementProgressForHabbo(this.getUserId(), snooow);
        } else {
            snooowProgress = owner.getHabboStats().getAchievementProgress(snooow);
        }
        int snooowDifference = room.getRoomSpecialTypes().getItemsOfType(InteractionSnowboardSlope.class).size() - snooowProgress;

        if (snooowDifference > 0) {
            if (owner != null) {
                AchievementManager.progressAchievement(owner, snooow, snooowDifference);
            } else {
                AchievementManager.progressAchievement(this.getUserId(), snooow, snooowDifference);
            }
        }

        super.onPlace(room);
    }

    @Override
    public boolean allowWiredResetState() {
        return true;
    }
}
