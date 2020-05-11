package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.catalog.CatalogManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertKeys;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import gnu.trove.map.hash.THashMap;

import java.util.concurrent.TimeUnit;


public class AboutCommand extends Command {
    public AboutCommand() {
        super(null, new String[]{"about", "info", "online", "server"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) {
        Emulator.getRuntime().gc();

        int seconds = Emulator.getIntUnixTimestamp() - Emulator.getTimeStarted();
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        if (Emulator.getConfig().getBoolean("info.shown", true)) {
            if (params.length >= 0) {



                THashMap<String, String> codes = new THashMap<>();
                codes.put("ONLINES", Emulator.getGameEnvironment().getHabboManager().getOnlineCount() + "");
                codes.put("QUARTOS", Emulator.getGameEnvironment().getRoomManager().getActiveRooms().size() + "");
                codes.put("MESSAGE", "DFWEFDEF</b>\r foda-se");
                //codes.put("USERNAME", gameClient.getHabbo().getHabboInfo().getUsername());
                //codes.put("LOOK", gameClient.getHabbo().getHabboInfo().getLook());
                //codes.put("TIME", Emulator.getDate().toString());
                gameClient.sendResponse(new BubbleAlertComposer("hybbe.sobre",
                        "<b>Creditos:</b>\r" +
                                "Arcturus (TheGeneral) \r" +
                                "Krews DEVs \r" +
                                "KOP Network \r" +
                                "\n" +
                                "<b>Licença:</b>\r" +
                                "Habbroi Hotel\n" +
                                "\n" +
                                "<b>Última atualização:</b> 05/04/2020\r" +
                                "\n"));
            }




            // THashMap<String, String> codes = new THashMap<>();
            //codes.put("ONLINES", Emulator.getGameEnvironment().getHabboManager().getOnlineCount() + "");
            // codes.put("QUARTOS", Emulator.getGameEnvironment().getRoomManager().getActiveRooms().size() + "");
            //codes.put("USERNAME", gameClient.getHabbo().getHabboInfo().getUsername());
            //codes.put("LOOK", gameClient.getHabbo().getHabboInfo().getLook());
            //codes.put("TIME", Emulator.getDate().toString());

            //StringBuilder message = new StringBuilder(String.valueOf(new BubbleAlertComposer("hybbe.sobre", codes).compose()));

            //gameClient.getHabbo().alert(message.toString());


            // String message = "<b>" + Emulator.version + "</b>\r\n";

            //if (Emulator.getConfig().getBoolean("info.shown", true)) {
            // message += "<b>Hotel Statistics</b>\r" +
            //         "- Online Users: " + Emulator.getGameEnvironment().getHabboManager().getOnlineCount() + "\r" +
            //         "- Active Rooms: " + Emulator.getGameEnvironment().getRoomManager().getActiveRooms().size() + "\r" +
            //         "- Shop:  " + Emulator.getGameEnvironment().getCatalogManager().catalogPages.size() + " pages and " + CatalogManager.catalogItemAmount + " items. \r" +
            //         "- Furni: " + Emulator.getGameEnvironment().getItemManager().getItems().size() + " item definitions" + "\r" +
            //         "\n" +
            //        "<b>Server Statistics</b>\r" +
            //       "- Uptime: " + day + (day > 1 ? " days, " : " day, ") + hours + (hours > 1 ? " hours, " : " hour, ") + minute + (minute > 1 ? " minutes, " : " minute, ") + second + (second > 1 ? " seconds!" : " second!") + "\r" +
            //        "- RAM Usage: " + (Emulator.getRuntime().totalMemory() - Emulator.getRuntime().freeMemory()) / (1024 * 1024) + "/" + (Emulator.getRuntime().freeMemory()) / (1024 * 1024) + "MB\r" +
            //        "- CPU Cores: " + Emulator.getRuntime().availableProcessors() + "\r" +
            //        "- Total Memory: " + Emulator.getRuntime().maxMemory() / (1024 * 1024) + "MB" + "\r\n";
            // }

            // message += "\r" +

            //   "<b>Thanks for using Arcturus. Report issues on the forums. http://arcturus.wf \r\r" +
            ///   "    - The General";

        }
        return true;
    }
}
