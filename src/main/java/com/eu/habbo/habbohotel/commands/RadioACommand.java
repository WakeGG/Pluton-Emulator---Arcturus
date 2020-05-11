package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.CustomNotificationComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import gnu.trove.map.hash.THashMap;

public class RadioACommand extends Command {
    public RadioACommand() {
        super("cmd_alert", Emulator.getTexts().getValue("commands.keys.alertan345345").split(";"));
    }

    public boolean handle(GameClient gc, String[] params) throws Exception {

        if (params.length < 2) {
                String message = "Lista de comandos\r\n";
                message = message + "" +
                        ":radio m [mensagem] > Envia uma notificação geral\r\n" +
                        ":radio loc > Envia notificação com link do quarto\r\n" +
                        ":radio g > Envia um alerta a todos os onlines\r\n";
                gc.sendResponse(new ModToolIssueHandledComposer(message));
            return true;
        }
        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("m")) {
                String messagem = "";

                for (int i = 2; i < params.length; ++i) {
                    messagem = messagem + params[i];
                    messagem = messagem + " ";
                }

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/hotelalert.png");
                keys.put("message", "gheheh");
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }

        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("img")) {
                String messagem = params[2];
                String message = "";

                for (int i = 3; i < params.length; ++i) {
                    message = message + params[i];
                    message = message + " ";
                }

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/" + messagem + ".png");
                keys.put("message", message);
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }

        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("quarto")) {
                String messagem = params[2];
                String message = "";

                for (int i = 3; i < params.length; ++i) {
                    message = message + params[i];
                    message = message + " ";
                }

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/" + messagem + ".png");
                keys.put("linkUrl", "event:navigator/goto/" + gc.getHabbo().getHabboInfo().getCurrentRoom().getId() + "");
                keys.put("message", message + ", clique aqui para ir");
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("ltd")) {

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/ltd.png");
                keys.put("linkUrl", "event:catalog/open/monday");
                keys.put("message", "Parece que tem novos raros limitados, confira agora mesmo!");
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }

        if (params.length > 0) {
            if (params[1].equalsIgnoreCase("on")) {

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/polaroid_photo.png");
                keys.put("message", "Estão " + Emulator.getGameEnvironment().getHabboManager().getOnlineCount() + " usuários online, ajude a divulgar o hotel!");
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }
        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("lol")) {
                String messagem = "";

                for (int i = 2; i < params.length; ++i) {
                    messagem = messagem + params[i];
                    messagem = messagem + " ";
                }
                //Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new CustomNotificationComposer(CustomNotificationComposer.HOPPER_NO_HC));

                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new CustomNotificationComposer(CustomNotificationComposer.STARS_NOT_ENOUGH_USERS));

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/hotelalert.png");
                keys.put("message", messagem);
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }
        else {
            gc.getHabbo().whisper(Emulator.getTexts().getValue("essentials.command.bubblealert.forgot_message"), RoomChatMessageBubbles.ALERT);
        }

        return true;
    }
}
