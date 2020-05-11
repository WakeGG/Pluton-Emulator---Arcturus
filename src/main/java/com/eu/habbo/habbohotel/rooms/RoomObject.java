package com.eu.habbo.habbohotel.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionFurniHaveFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionFurniHaveHabbo;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionFurniTypeMatch;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionMatchStatePosition;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionNotFurniHaveFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionNotFurniHaveHabbo;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionNotFurniTypeMatch;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionNotMatchStatePosition;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionNotTriggerOnFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionTriggerOnFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectBotTeleport;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectBotWalkToFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectChangeFurniDirection;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectLowerFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectMatchFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectMatchFurniStaff;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectMoveFurniAway;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectMoveFurniTo;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectMoveFurniTowards;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectMoveRotateFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectRaiseFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectTeleport;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectToggleFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.triggers.WiredTriggerBotReachedFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.triggers.WiredTriggerFurniStateToggled;
import com.eu.habbo.habbohotel.items.interactions.wired.triggers.WiredTriggerHabboWalkOffFurni;
import com.eu.habbo.habbohotel.items.interactions.wired.triggers.WiredTriggerHabboWalkOnFurni;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import gnu.trove.iterator.hash.TObjectHashIterator;
import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class RoomObject {
    public final String name;
    public final String description;
    public final String model;
    public final String password;
    public final int usersMax;
    public final String paperFloor;
    public final String paperWall;
    public final String paperLandscape;
    public final int thicknessWall;
    public final int wallHeight;
    public final int thicknessFloor;
    public final String moodlightData;
    public final int rollerSpeed;
    public final RoomModelObject modelObject;
    public final FurniObject[] items;
    private transient int newRoomId;
    private transient THashMap<Integer, Integer> originalToNewFurniIdMap;
    private transient THashMap<Integer, Integer> newToOriginalFurniIdMap;
    public transient THashMap<String, Integer> missingFurnitureMap = new THashMap();
    private transient int ownerId;
    private transient String ownerName;
    private static String[] parts = new String[]{";", ":", "\t", "\r"};

    public RoomObject(Room room) {
        this.name = room.getName();
        this.description = room.getDescription();
        this.model = room.getLayout().getName();
        this.password = room.getPassword();
        this.usersMax = room.getUsersMax();
        this.paperFloor = room.getFloorPaint();
        this.paperWall = room.getWallPaint();
        this.paperLandscape = room.getBackgroundPaint();
        this.thicknessWall = room.getWallSize();
        this.wallHeight = room.getWallHeight();
        this.thicknessFloor = room.getFloorSize();
        this.moodlightData = "";
        this.rollerSpeed = room.getRollerSpeed();
        if (room.hasCustomLayout()) {
            this.modelObject = new RoomModelObject(room.getLayout().getDoorX(), room.getLayout().getDoorY(), room.getLayout().getDoorDirection(), room.getLayout().getHeightmap());
        } else {
            this.modelObject = null;
        }

        THashSet<HabboItem> roomItems = room.getFloorItems();
        roomItems.addAll(room.getWallItems());
        this.items = new FurniObject[roomItems.size()];
        int index = 0;

        for(TObjectHashIterator var4 = roomItems.iterator(); var4.hasNext(); ++index) {
            HabboItem item = (HabboItem)var4.next();
            this.items[index] = new FurniObject(item);
        }

    }

    public int insertRoom(Habbo owner) {
        this.ownerId = owner.getHabboInfo().getId();
        this.ownerName = owner.getHabboInfo().getUsername();

        try {
            Connection connection = Emulator.getDatabase().getDataSource().getConnection();
            Throwable var3 = null;

            try {
                PreparedStatement insertCustomModelStatement = connection.prepareStatement("INSERT INTO rooms (name, description, model, password, users_max, paper_floor, paper_wall, paper_landscape, thickness_wall, wall_height, thickness_floor, moodlight_data, roller_speed, owner_id, owner_name, override_model) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
                Throwable var5 = null;

                try {
                    insertCustomModelStatement.setString(1, this.name);
                    insertCustomModelStatement.setString(2, this.description);
                    insertCustomModelStatement.setString(3, this.model);
                    insertCustomModelStatement.setString(4, this.password);
                    insertCustomModelStatement.setInt(5, this.usersMax);
                    insertCustomModelStatement.setString(6, this.paperFloor);
                    insertCustomModelStatement.setString(7, this.paperWall);
                    insertCustomModelStatement.setString(8, this.paperLandscape);
                    insertCustomModelStatement.setInt(9, this.thicknessWall);
                    insertCustomModelStatement.setInt(10, this.wallHeight);
                    insertCustomModelStatement.setInt(11, this.thicknessFloor);
                    insertCustomModelStatement.setString(12, this.moodlightData);
                    insertCustomModelStatement.setInt(13, this.rollerSpeed);
                    insertCustomModelStatement.setInt(14, this.ownerId);
                    insertCustomModelStatement.setString(15, this.ownerName);
                    insertCustomModelStatement.setString(16, this.overrideModel() ? "1" : "0");
                    insertCustomModelStatement.execute();
                    ResultSet set = insertCustomModelStatement.getGeneratedKeys();
                    Throwable var7 = null;

                    try {
                        if (set.next()) {
                            this.newRoomId = set.getInt(1);
                        }
                    } catch (Throwable var84) {
                        var7 = var84;
                        throw var84;
                    } finally {
                        if (set != null) {
                            if (var7 != null) {
                                try {
                                    set.close();
                                } catch (Throwable var82) {
                                    var7.addSuppressed(var82);
                                }
                            } else {
                                set.close();
                            }
                        }

                    }
                } catch (Throwable var87) {
                    var5 = var87;
                    throw var87;
                } finally {
                    if (insertCustomModelStatement != null) {
                        if (var5 != null) {
                            try {
                                insertCustomModelStatement.close();
                            } catch (Throwable var81) {
                                var5.addSuppressed(var81);
                            }
                        } else {
                            insertCustomModelStatement.close();
                        }
                    }

                }

                if (this.overrideModel()) {
                    insertCustomModelStatement = connection.prepareStatement("INSERT INTO room_models_custom (id, name, door_x, door_y, door_dir, heightmap) VALUES (?, ?, ?, ?, ?, ?)");
                    var5 = null;

                    try {
                        insertCustomModelStatement.setInt(1, this.newRoomId);
                        insertCustomModelStatement.setString(2, "custom_" + this.newRoomId);
                        insertCustomModelStatement.setInt(3, this.modelObject.doorX);
                        insertCustomModelStatement.setInt(4, this.modelObject.doorY);
                        insertCustomModelStatement.setInt(5, this.modelObject.doorDir);
                        insertCustomModelStatement.setString(6, this.modelObject.heightMap);
                        insertCustomModelStatement.execute();
                    } catch (Throwable var83) {
                        var5 = var83;
                        throw var83;
                    } finally {
                        if (insertCustomModelStatement != null) {
                            if (var5 != null) {
                                try {
                                    insertCustomModelStatement.close();
                                } catch (Throwable var80) {
                                    var5.addSuppressed(var80);
                                }
                            } else {
                                insertCustomModelStatement.close();
                            }
                        }

                    }
                }
            } catch (Throwable var89) {
                var3 = var89;
                throw var89;
            } finally {
                if (connection != null) {
                    if (var3 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var79) {
                            var3.addSuppressed(var79);
                        }
                    } else {
                        connection.close();
                    }
                }

            }
        } catch (Exception var91) {
            Emulator.getLogging().logErrorLine(var91);
        }

        return this.newRoomId;
    }

    public void insertFurniture() {
        this.originalToNewFurniIdMap = new THashMap();
        this.newToOriginalFurniIdMap = new THashMap();

        Connection connection;
        Throwable var2;
        PreparedStatement statement;
        Throwable var4;
        int var7;
        try {
            connection = Emulator.getDatabase().getDataSource().getConnection();
            var2 = null;

            try {
                statement = connection.prepareStatement("INSERT INTO items (user_id, room_id, item_id, wall_pos, x, y, z, rot, extra_data, wired_data, limited_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 1);
                var4 = null;

                try {
                    List<Integer> itemIds = new ArrayList();
                    FurniObject[] var6 = this.items;
                    var7 = var6.length;

                    int count;
                    for(count = 0; count < var7; ++count) {
                        FurniObject object = var6[count];
                        Item baseItem = Emulator.getGameEnvironment().getItemManager().getItem(object.name);
                        if (baseItem == null) {
                            if (!this.missingFurnitureMap.containsKey(object.name)) {
                                this.missingFurnitureMap.put(object.name, 0);
                            }

                            this.missingFurnitureMap.put(object.name, (Integer)this.missingFurnitureMap.get(object.name) + 1);
                        } else {
                            statement.setInt(1, this.ownerId);
                            statement.setInt(2, this.newRoomId);
                            statement.setInt(3, baseItem.getId());
                            statement.setString(4, object.wallPosition);
                            statement.setInt(5, object.x);
                            statement.setInt(6, object.y);
                            statement.setDouble(7, object.z);
                            statement.setInt(8, object.rotation);
                            statement.setString(9, object.extradata);
                            statement.setString(10, "");
                            statement.setString(11, object.limitedData);
                            statement.addBatch();
                            itemIds.add(object.id);
                        }
                    }

                    statement.executeLargeBatch();
                    ResultSet set = statement.getGeneratedKeys();
                    Throwable var143 = null;

                    try {
                        for(count = 0; set.next(); ++count) {
                            this.originalToNewFurniIdMap.put(itemIds.get(count), set.getInt(1));
                            this.newToOriginalFurniIdMap.put(set.getInt(1), itemIds.get(count));
                        }
                    } catch (Throwable var131) {
                        var143 = var131;
                        throw var131;
                    } finally {
                        if (set != null) {
                            if (var143 != null) {
                                try {
                                    set.close();
                                } catch (Throwable var125) {
                                    var143.addSuppressed(var125);
                                }
                            } else {
                                set.close();
                            }
                        }

                    }
                } catch (Throwable var133) {
                    var4 = var133;
                    throw var133;
                } finally {
                    if (statement != null) {
                        if (var4 != null) {
                            try {
                                statement.close();
                            } catch (Throwable var124) {
                                var4.addSuppressed(var124);
                            }
                        } else {
                            statement.close();
                        }
                    }

                }
            } catch (Throwable var135) {
                var2 = var135;
                throw var135;
            } finally {
                if (connection != null) {
                    if (var2 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var123) {
                            var2.addSuppressed(var123);
                        }
                    } else {
                        connection.close();
                    }
                }

            }
        } catch (Exception var137) {
            Emulator.getLogging().logErrorLine(var137);
        }

        Iterator var138 = this.originalToNewFurniIdMap.entrySet().iterator();

        while(var138.hasNext()) {
            Entry<Integer, Integer> entry = (Entry)var138.next();
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        try {
            connection = Emulator.getDatabase().getDataSource().getConnection();
            var2 = null;

            try {
                statement = connection.prepareStatement("UPDATE items SET wired_data = ? WHERE id = ? LIMIT 1");
                var4 = null;

                try {
                    FurniObject[] var140 = this.items;
                    int var142 = var140.length;

                    for(var7 = 0; var7 < var142; ++var7) {
                        FurniObject item = var140[var7];
                        if (item.isWired) {
                            System.out.println(item.wiredData);
                            Iterator var145 = this.originalToNewFurniIdMap.entrySet().iterator();

                            while(var145.hasNext()) {
                                Entry<Integer, Integer> entry = (Entry)var145.next();

                                for(int i = 0; i < parts.length; ++i) {
                                    for(int j = 0; j < parts.length; ++j) {
                                        item.wiredData = item.wiredData.replace(parts[i] + "" + entry.getKey() + "" + parts[j], parts[i] + "" + entry.getValue() + "" + parts[j]);
                                    }
                                }
                            }

                            System.out.println(item.wiredData);
                            statement.setString(1, item.wiredData);
                            statement.setInt(2, (Integer)this.originalToNewFurniIdMap.get(item.id));
                            statement.execute();
                        }
                    }
                } catch (Throwable var126) {
                    var4 = var126;
                    throw var126;
                } finally {
                    if (statement != null) {
                        if (var4 != null) {
                            try {
                                statement.close();
                            } catch (Throwable var122) {
                                var4.addSuppressed(var122);
                            }
                        } else {
                            statement.close();
                        }
                    }

                }
            } catch (Throwable var128) {
                var2 = var128;
                throw var128;
            } finally {
                if (connection != null) {
                    if (var2 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var121) {
                            var2.addSuppressed(var121);
                        }
                    } else {
                        connection.close();
                    }
                }

            }
        } catch (SQLException var130) {
            Emulator.getLogging().logSQLException(var130);
        }

    }

    private boolean isFurniWired(Item item) {
        return item.getInteractionType().getType() == WiredConditionFurniHaveFurni.class || item.getInteractionType().getType() == WiredConditionFurniHaveHabbo.class || item.getInteractionType().getType() == WiredConditionFurniTypeMatch.class || item.getInteractionType().getType() == WiredConditionMatchStatePosition.class || item.getInteractionType().getType() == WiredConditionNotFurniHaveFurni.class || item.getInteractionType().getType() == WiredConditionNotFurniHaveHabbo.class || item.getInteractionType().getType() == WiredConditionNotFurniTypeMatch.class || item.getInteractionType().getType() == WiredConditionNotMatchStatePosition.class || item.getInteractionType().getType() == WiredConditionTriggerOnFurni.class || item.getInteractionType().getType() == WiredConditionNotTriggerOnFurni.class || item.getInteractionType().getType() == WiredEffectBotWalkToFurni.class || item.getInteractionType().getType() == WiredEffectChangeFurniDirection.class || item.getInteractionType().getType() == WiredEffectLowerFurni.class || item.getInteractionType().getType() == WiredEffectMatchFurni.class || item.getInteractionType().getType() == WiredEffectRaiseFurni.class || item.getInteractionType().getType() == WiredEffectToggleFurni.class || item.getInteractionType().getType() == WiredEffectMatchFurniStaff.class || item.getInteractionType().getType() == WiredEffectMoveFurniAway.class || item.getInteractionType().getType() == WiredEffectMoveFurniTo.class || item.getInteractionType().getType() == WiredEffectMoveFurniTowards.class || item.getInteractionType().getType() == WiredEffectMoveRotateFurni.class || item.getInteractionType().getType() == WiredEffectTeleport.class || item.getInteractionType().getType() == WiredEffectBotTeleport.class || item.getInteractionType().getType() == WiredTriggerFurniStateToggled.class || item.getInteractionType().getType() == WiredTriggerHabboWalkOffFurni.class || item.getInteractionType().getType() == WiredTriggerHabboWalkOnFurni.class || item.getInteractionType().getType() == WiredTriggerBotReachedFurni.class;
    }

    public int getNewRoomId() {
        return this.newRoomId;
    }

    private boolean overrideModel() {
        return this.modelObject != null && !this.modelObject.heightMap.isEmpty();
    }
}
