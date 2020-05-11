package com.eu.habbo.core;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboInfo;
import com.eu.habbo.habbohotel.users.HabboManager;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.util.Map;

public class PixelScheduler extends Scheduler {

    public static boolean IGNORE_HOTEL_VIEW;
    public static boolean IGNORE_IDLED;

    public PixelScheduler() {
        super(Emulator.getConfig().getInt("hotel.auto.pixels.interval"));
        this.reloadConfig();
    }

    public void reloadConfig() {
        if (Emulator.getConfig().getBoolean("hotel.auto.pixels.enabled")) {
            IGNORE_HOTEL_VIEW = Emulator.getConfig().getBoolean("hotel.auto.pixels.ignore.hotelview");
            IGNORE_IDLED = Emulator.getConfig().getBoolean("hotel.auto.pixels.ignore.idled");
            if (this.disposed) {
                this.disposed = false;
                this.run();
            }
        } else {
            this.disposed = true;
        }
    }

    @Override
    public void run() {
        super.run();

        Habbo habbo;
        for (Map.Entry<Integer, Habbo> map : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().entrySet()) {
            habbo = map.getValue();
            try {

                if (habbo.getHabboInfo().getRank().getId() == 1) {
                    if (habbo != null) {

                        habbo.giveCredits(1000);
                        habbo.givePixels(10);
                        habbo.givePoints(2);

                        THashMap<String, String> points_keys = new THashMap<String, String>();
                        points_keys.put("display", "BUBBLE");
                        points_keys.put("image", "${image.library.url}notifications/dmd.png");
                        points_keys.put("message", "Voce ganhou 2 diamantes e 10 duckets por estar online no habbroi");
                        habbo.getClient().sendResponse(new BubbleAlertComposer("receivedpoints", points_keys));
                    }
            }

                if (habbo.getHabboInfo().getRank().getId() == 2) {
                    if (habbo != null) {

                        habbo.giveCredits(2000);
                        habbo.givePixels(20);
                        habbo.givePoints(3);

                        THashMap<String, String> points_keys = new THashMap<String, String>();
                        points_keys.put("display", "BUBBLE");
                        points_keys.put("image", "${image.library.url}notifications/vips.png");
                        points_keys.put("message", "Voce ganhou 3 diamantes e 20 duckets por estar online no habbroi");
                        habbo.getClient().sendResponse(new BubbleAlertComposer("receivedpoints", points_keys));
                    }
                }

                if (habbo.getHabboInfo().getRank().getId() >= 3) {
                    if (habbo != null) {

                        habbo.giveCredits(1000);
                        habbo.givePixels(10);
                        habbo.givePoints(2);

                        THashMap<String, String> points_keys = new THashMap<String, String>();
                        points_keys.put("display", "BUBBLE");
                        points_keys.put("image", "${image.library.url}notifications/dmd.png");
                        points_keys.put("message", "Voce ganhou 2 diamantes e 10 duckets por estar online no habbroi");
                        habbo.getClient().sendResponse(new BubbleAlertComposer("receivedpoints", points_keys));
                    }
                }

            } catch (Exception e) {
                Emulator.getLogging().logErrorLine(e);
            }
        }
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }
}
