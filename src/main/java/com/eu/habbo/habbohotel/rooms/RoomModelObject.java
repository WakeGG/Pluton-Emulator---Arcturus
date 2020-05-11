package com.eu.habbo.habbohotel.rooms;

public class RoomModelObject {
    public final int doorX;
    public final int doorY;
    public final int doorDir;
    public final String heightMap;

    public RoomModelObject(int doorX, int doorY, int doorDir, String heightMap) {
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorDir = doorDir;
        this.heightMap = heightMap;
    }
}
