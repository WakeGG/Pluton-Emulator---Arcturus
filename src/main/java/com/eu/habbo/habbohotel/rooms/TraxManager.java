package com.eu.habbo.habbohotel.rooms;

import com.eu.habbo.Emulator;
import com.eu.habbo.core.Disposable;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.SoundTrack;
import com.eu.habbo.habbohotel.items.interactions.InteractionJukeBox;
import com.eu.habbo.habbohotel.items.interactions.InteractionMusicDisc;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.inventory.AddHabboItemComposer;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import com.eu.habbo.messages.outgoing.inventory.RemoveHabboItemComposer;
import com.eu.habbo.messages.outgoing.rooms.items.jukebox.JukeBoxMySongsComposer;
import com.eu.habbo.messages.outgoing.rooms.items.jukebox.JukeBoxNowPlayingMessageComposer;
import com.eu.habbo.messages.outgoing.rooms.items.jukebox.JukeBoxPlayListComposer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TraxManager implements Disposable {
    private final Room room;
    private final List<InteractionMusicDisc> songs = new ArrayList<>(0);
    private int totalLength = 0;
    private int startedTimestamp = 0;
    private InteractionMusicDisc currentlyPlaying = null;
    private int playingIndex = 0;
    private int cycleStartedTimestamp = 0;
    private Habbo starter = null;

    private boolean disposed = false;

    public TraxManager(Room room) {
        this.room = room;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM room_trax_playlist WHERE room_id = ?")) {
            statement.setInt(1, this.room.getId());
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    HabboItem musicDisc = Emulator.getGameEnvironment().getItemManager().loadHabboItem(set.getInt("item_id"));

                    if(musicDisc != null) {
                        if (!(musicDisc instanceof  InteractionMusicDisc) || musicDisc.getRoomId() != -1) {
                            try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM room_trax_playlist WHERE room_id = ? AND item_id = ? LIMIT 1")) {
                                stmt.setInt(1, this.room.getId());
                                stmt.setInt(2, musicDisc.getId());
                                stmt.execute();
                            } catch (SQLException e) {
                                Emulator.getLogging().logSQLException(e);
                                return;
                            }
                        } else {
                            SoundTrack track = Emulator.getGameEnvironment().getItemManager().getSoundTrack(((InteractionMusicDisc) musicDisc).getSongId());

                            if (track != null) {
                                this.songs.add((InteractionMusicDisc) musicDisc);
                                this.totalLength += track.getLength();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }
    }

    public void cycle() {
        if (this.isPlaying()) {
            if (this.timePlaying() >= this.totalLength) {
                this.play(0);
                //restart
            }

            if (this.currentSong() != null && Emulator.getIntUnixTimestamp() >= this.startedTimestamp + this.currentSong().getLength()) {
                this.play((this.playingIndex + 1) % this.songs.size());
            }
        }
    }

    public void play(int index) {
        this.play(index, null);
    }

    public void play(int index, Habbo starter) {
        if (this.currentlyPlaying == null) {
            for (HabboItem item : this.room.getRoomSpecialTypes().getItemsOfType(InteractionJukeBox.class)) {
                item.setExtradata("1");
                this.room.updateItem(item);
            }
        }

        if (!this.songs.isEmpty()) {
            index = index % this.songs.size();

            this.currentlyPlaying = this.songs.get(index);

            if (this.currentlyPlaying != null) {
                this.room.setJukeBoxActive(true);
                this.startedTimestamp = Emulator.getIntUnixTimestamp();
                this.playingIndex = index;

                if (starter != null) {
                    this.starter = starter;
                    this.cycleStartedTimestamp = Emulator.getIntUnixTimestamp();
                }
            }

            this.room.sendComposer(new JukeBoxNowPlayingMessageComposer(Emulator.getGameEnvironment().getItemManager().getSoundTrack(this.currentlyPlaying.getSongId()), this.playingIndex, 0).compose());
        } else {
            this.stop();
        }
    }

    public void stop() {
        if (this.starter != null && this.cycleStartedTimestamp > 0) {
            AchievementManager.progressAchievement(this.starter, Emulator.getGameEnvironment().getAchievementManager().getAchievement("MusicPlayer"), (Emulator.getIntUnixTimestamp() - cycleStartedTimestamp) / 60);
        }

        this.room.setJukeBoxActive(false);
        this.currentlyPlaying = null;
        this.startedTimestamp = 0;
        this.cycleStartedTimestamp = 0;
        this.starter = null;
        this.playingIndex = 0;

        for (HabboItem item : this.room.getRoomSpecialTypes().getItemsOfType(InteractionJukeBox.class)) {
            item.setExtradata("0");
            this.room.updateItem(item);
        }

        this.room.sendComposer(new JukeBoxNowPlayingMessageComposer(null, -1, 0).compose());
    }

    public SoundTrack currentSong() {
        if (!this.songs.isEmpty() && this.playingIndex < this.songs.size()) {
            return Emulator.getGameEnvironment().getItemManager().getSoundTrack(this.songs.get(this.playingIndex).getSongId());
        }
        return null;
    }

    public void addSong(InteractionMusicDisc musicDisc, Habbo habbo) {
        SoundTrack track = Emulator.getGameEnvironment().getItemManager().getSoundTrack(musicDisc.getSongId());

        if (track != null) {
            this.totalLength += track.getLength();
            this.songs.add(musicDisc);

            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("INSERT INTO room_trax_playlist (room_id, item_id) VALUES (?, ?)")) {
                statement.setInt(1, this.room.getId());
                statement.setInt(2, musicDisc.getId());
                statement.execute();
            } catch (SQLException e) {
                Emulator.getLogging().logSQLException(e);
                return;
            }

            this.room.sendComposer(new JukeBoxPlayListComposer(this.songs, this.totalLength).compose());

            musicDisc.setRoomId(-1);
            musicDisc.needsUpdate(true);

            habbo.getInventory().getItemsComponent().removeHabboItem(musicDisc);
            habbo.getClient().sendResponse(new RemoveHabboItemComposer(musicDisc.getGiftAdjustedId()));
        }

        this.sendUpdatedSongList();
    }

    public void removeSong(int itemId) {
        InteractionMusicDisc musicDisc = this.fromItemId(itemId);

        if (musicDisc != null) {
            this.songs.remove(musicDisc);

            try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM room_trax_playlist WHERE room_id = ? AND item_id = ? LIMIT 1")) {
                statement.setInt(1, this.room.getId());
                statement.setInt(2, itemId);
                statement.execute();
            } catch (SQLException e) {
                Emulator.getLogging().logSQLException(e);
                return;
            }

            this.totalLength -= Emulator.getGameEnvironment().getItemManager().getSoundTrack(musicDisc.getSongId()).getLength();
            if (this.currentlyPlaying == musicDisc) {
                this.play(this.playingIndex);
            }

            this.room.sendComposer(new JukeBoxPlayListComposer(this.songs, this.totalLength).compose());

            musicDisc.setRoomId(0);
            musicDisc.needsUpdate(true);

            Habbo owner = Emulator.getGameEnvironment().getHabboManager().getHabbo(musicDisc.getUserId());

            if (owner != null) {
                owner.getInventory().getItemsComponent().addItem(musicDisc);

                GameClient client = owner.getClient();
                if (client != null) {
                    client.sendResponse(new AddHabboItemComposer(musicDisc));
                    client.sendResponse(new InventoryRefreshComposer());
                }
            }
        }

        this.sendUpdatedSongList();
    }

    public void sendUpdatedSongList() {
        this.room.getHabbos().forEach(h -> {
            GameClient client = h.getClient();

            if (client != null) {
                client.sendResponse(new JukeBoxMySongsComposer(this.myList(h)));
            }
        });
    }

    public int timePlaying() {
        return Emulator.getIntUnixTimestamp() - this.startedTimestamp;
    }

    public int totalLength() {
        return this.totalLength;
    }

    public List<InteractionMusicDisc> getSongs() {
        return this.songs;
    }

    public boolean isPlaying() {
        return this.currentlyPlaying != null;
    }

    public List<SoundTrack> soundTrackList() {
        List<SoundTrack> trax = new ArrayList<>(this.songs.size());

        for (InteractionMusicDisc musicDisc : this.songs) {
            SoundTrack track = Emulator.getGameEnvironment().getItemManager().getSoundTrack(musicDisc.getSongId());

            if (track != null) {
                trax.add(track);
            }
        }

        return trax;
    }

    public List<InteractionMusicDisc> myList(Habbo habbo) {
        return habbo.getInventory().getItemsComponent().getItems().valueCollection().stream()
                .filter(i -> i instanceof InteractionMusicDisc && i.getRoomId() == 0)
                .map(i -> (InteractionMusicDisc) i)
                .collect(Collectors.toList());
    }

    public InteractionMusicDisc fromItemId(int itemId) {
        for (InteractionMusicDisc musicDisc : this.songs) {
            if (musicDisc != null && musicDisc.getId() == itemId) {
                return musicDisc;
            }
        }

        return null;
    }

    public void clearPlayList() {
        this.songs.clear();
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM room_trax_playlist WHERE room_id = ?")) {
            statement.setInt(1, this.room.getId());
            statement.execute();
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }
    }

    public void updateCurrentPlayingSong(Habbo habbo) {
        if (this.isPlaying()) {
            habbo.getClient().sendResponse(new JukeBoxNowPlayingMessageComposer(Emulator.getGameEnvironment().getItemManager().getSoundTrack(this.currentlyPlaying.getSongId()), this.playingIndex, 1000 * (Emulator.getIntUnixTimestamp() - this.startedTimestamp)));
        } else {
            habbo.getClient().sendResponse(new JukeBoxNowPlayingMessageComposer(null, -1, 0));
        }
    }

    @Override
    public void dispose() {
        this.disposed = true;
    }

    @Override
    public boolean disposed() {
        return this.disposed;
    }
}
