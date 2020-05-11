package com.eu.habbo.habbohotel.commands;


import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PremiarCommand extends Command {
    public PremiarCommand() {
        super("cmd_massduckets", Emulator.getTexts().getValue("hybbe.cmd_premiar.keysa").split(";"));
    }

    public boolean handle(GameClient gameClient, String[] params) {
        if (params.length < 2) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.erro.cmd_premiar.faltanome"), RoomChatMessageBubbles.ALERT);
            return true;
        } else {
            String targetUsername = params[1];

            for(int i = 2; i < params.length; ++i) {
            }
            Habbo habbo = Emulator.getGameEnvironment().getHabboManager().getHabbo(params[1]);
                if (habbo != null )  {
                    AchievementManager.progressAchievement(habbo, Emulator.getGameEnvironment().getAchievementManager().getAchievement(Emulator.getConfig().getValue("premiar.conquista")));
                    habbo.givePoints(Emulator.getConfig().getInt("hotel.welcome.points", 15));
                    habbo.givePixels(Emulator.getConfig().getInt("hotel.welcome.pixels", 3));
                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement premiar = connection.createStatement()) {
                    String sql = "UPDATE users SET premiar = premiar + '1' WHERE username = ? ";
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    try {
                        pstmt.setString(1, habbo.getHabboInfo().getUsername());

                        pstmt.executeUpdate();
                    } finally {

                    }
                }
                catch (SQLException e){}
                gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_points.invalid_amount"), RoomChatMessageBubbles.ALERT);
                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/evento.png");
                //keys.put("image", "${image.library.url}notifications/user.php?p=%figure%".replace("%figure%", event.habbo.getHabboInfo().getLook()));
                //keys.put("image", "habbo-imaging/avatarimage.php?figure=" + avatar + "&headonly=1&size=m&gesture=sml&direction=2&head_direction=2&action=std&img_format=png");
                //keys.put("image", "/habbo-imaging/avatarimage.php?figure=" + avatar + "&headonly=1&size=m&gesture=sml&direction=2&head_direction=2&action=std&img_format=png");
                //keys.put("image", Emulator.getConfig().getValue("alerta.imagem").replace("%fig%", habbo.getHabboInfo().getLook()));
                //keys.put("image", "fig/" + habbo.getHabboInfo().getLook() + "&head_direction=2&headonly=1");
                keys.put("image","http://localhost/premiar/" + habbo.getHabboInfo().getLook() + "&headonly=1&head_direction=3");
                keys.put("USERNAME", habbo.getHabboInfo().getUsername());
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("gameinvite", keys));
            } else {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.erro.cmd_premiar.useroffline").replace("%user%", targetUsername), RoomChatMessageBubbles.ALERT);
                }

            Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

                Habbo kick = Emulator.getGameEnvironment().getHabboManager().getHabbo(targetUsername);
                if (!habbo.hasPermission(Permission.ACC_UNKICKABLE) && !habbo.hasPermission(Permission.ACC_SUPPORTTOOL) && !room.isOwner(habbo)) {
                    room.kickHabbo(kick, true);
                }
            }

        return true;
    }
}


