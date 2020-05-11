package com.eu.habbo.messages.incoming.users;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.catalog.CatalogManager;
import com.eu.habbo.habbohotel.users.HabboGender;
import com.eu.habbo.habbohotel.users.HabboManager;
import com.eu.habbo.habbohotel.users.inventory.WardrobeComponent;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.catalog.AlertPurchaseUnavailableComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.HotelWillCloseInMinutesComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import com.eu.habbo.plugin.events.users.UserSavedWardrobeEvent;
import com.eu.habbo.threading.runnables.ShutdownEmulator;

public class SaveWardrobeEvent extends MessageHandler {
    @Override
    public void handle() throws Exception {
        int slotId = this.packet.readInt();
        String look = this.packet.readString();
        String gender = this.packet.readString();

        WardrobeComponent.WardrobeItem wardrobeItem;
        if (this.client.getHabbo().getInventory().getWardrobeComponent().getLooks().containsKey(slotId)) {
            wardrobeItem = this.client.getHabbo().getInventory().getWardrobeComponent().getLooks().get(slotId);
            wardrobeItem.setGender(HabboGender.valueOf(gender));
            wardrobeItem.setLook(look);
            wardrobeItem.setNeedsUpdate(true);
        } else {
            wardrobeItem = this.client.getHabbo().getInventory().getWardrobeComponent().createLook(this.client.getHabbo(), slotId, look);
            wardrobeItem.setNeedsInsert(true);
        }

        int timeSinceLastUpdate = (Emulator.getIntUnixTimestamp()) - this.client.getHabbo().getHabboStats().getVisualTimestamp();


        UserSavedWardrobeEvent wardrobeEvent = new UserSavedWardrobeEvent(this.client.getHabbo(), wardrobeItem);
        Emulator.getPluginManager().fireEvent(wardrobeEvent);

        Emulator.getThreading().run(wardrobeItem);
        this.client.getHabbo().getHabboStats().setVisualTimestamp(Emulator.getIntUnixTimestamp());
        AchievementManager.progressAchievement(this.client.getHabbo(), Emulator.getGameEnvironment().getAchievementManager().getAchievement("HaibboLooksSave"));

    }
}

