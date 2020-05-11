package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class PremeiaCommand extends Command {
    public PremeiaCommand() {
        super("cmd_premiar", Emulator.getTexts().getValue("hybbe.cmd_premiar.keys").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        if (gameClient.getHabbo().getHabboInfo().getCurrentRoom() != null) {
            if (params.length == 2) {
                Habbo target = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);

                if (target == null) {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.error.cmd_control.not_found").replace("%user%", params[1]), RoomChatMessageBubbles.ALERT);
                    return true;
                }

                if (target == gameClient.getHabbo()) {
                   gameClient.getHabbo().whisper("Você não se pode premiar, troxa", RoomChatMessageBubbles.ALERT);
                   return true;
                }
                if (target.getHabboInfo().getRank().getId() >= gameClient.getHabbo().getHabboInfo().getRank().getId()) {
                    gameClient.getHabbo().whisper("Você não pode premiar membros da equipe.", RoomChatMessageBubbles.ALERT);
                   return true;
                }
                if (target != null) {
                    // CONQUISTA
                    AchievementManager.progressAchievement(target, Emulator.getGameEnvironment().getAchievementManager().getAchievement(Emulator.getConfig().getValue("premiar.conquista")));
                    //USER
                    if (target.getHabboInfo().getRank().getId() == 1) {
                        //MOEDAS
                        target.giveCredits(Emulator.getConfig().getInt("hybbe.premiar.moedas"));
                        //RUBIS
                        target.givePoints(Emulator.getConfig().getInt("hybbe.premiar.rubis"));
                        //GEMAS
                        target.givePixels(Emulator.getConfig().getInt("hybbe.premiar.gemas"));

                        THashMap<String, String> vipa = new THashMap();
                        vipa.put("display", "BUBBLE");
                        vipa.put("image", "${image.library.url}notifications/offer_default_icon.png");
                        vipa.put("message", "Você recebeu " + Emulator.getConfig().getInt("hybbe.premiar.rubis") + " diamantes e " + Emulator.getConfig().getInt("hybbe.premiar.gemas") + " duckets");
                        vipa.put("USERNAME", target.getHabboInfo().getUsername());
                        Habbo vip = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);
                        vip.getClient().sendResponse(new BubbleAlertComposer("event",vipa));
                    }
                    //VIP
                    if (target.getHabboInfo().getRank().getId() == 2) {
                        //MOEDAS
                        target.giveCredits(Emulator.getConfig().getInt("hybbe.premiarvip.moedas"));
                        //RUBIS
                        target.givePoints(Emulator.getConfig().getInt("hybbe.premiarvip.rubis"));
                        //GEMAS
                        target.givePixels(Emulator.getConfig().getInt("hybbe.premiarvip.gemas"));

                        THashMap<String, String> vipa = new THashMap();
                        vipa.put("display", "BUBBLE");
                        vipa.put("image", "${image.library.url}notifications/offer_default_icon.png");
                        vipa.put("message", "Você recebeu " + Emulator.getConfig().getInt("hybbe.premiarvip.rubis") + " diamantes e " + Emulator.getConfig().getInt("hybbe.premiarvip.gemas") + " duckets por ser membro VIP");
                        vipa.put("USERNAME", target.getHabboInfo().getUsername());
                        Habbo vip = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);
                        vip.getClient().sendResponse(new BubbleAlertComposer("event",vipa));
                    }
                    //OTHERs
                    if (target.getHabboInfo().getRank().getId() >= 3) {
                        //MOEDAS
                        target.giveCredits(Emulator.getConfig().getInt("hybbe.premiar.moedas"));
                        //RUBIS
                        target.givePoints(Emulator.getConfig().getInt("hybbe.premiar.rubis"));
                        //GEMAS
                        target.givePixels(Emulator.getConfig().getInt("hybbe.premiar.gemas"));

                        THashMap<String, String> vipa = new THashMap();
                        vipa.put("display", "BUBBLE");
                        vipa.put("image", "${image.library.url}notifications/offer_default_icon.png");
                        vipa.put("message", "Você recebeu " + Emulator.getConfig().getInt("hybbe.premiar.rubis") + " diamantes e " + Emulator.getConfig().getInt("hybbe.premiar.gemas") + " duckets");
                        vipa.put("USERNAME", target.getHabboInfo().getUsername());
                        Habbo vip = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);
                        vip.getClient().sendResponse(new BubbleAlertComposer("event",vipa));
                    }

                    //PONTOS
                    try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement premiar = connection.createStatement()) {
                        String sql = "UPDATE users SET premiar = premiar + '1' WHERE username = ? ";
                        PreparedStatement pstmt = connection.prepareStatement(sql);
                        try {
                            pstmt.setString(1, target.getHabboInfo().getUsername());
                            pstmt.executeUpdate();
                        } finally {
                        }
                    } catch (SQLException e) {
                    }

                    gameClient.getHabbo().getClient().getHabbo().whisper("Você premiou "+ target.getHabboInfo().getUsername()+ " com sucesso!", RoomChatMessageBubbles.ALERT);

                    Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();

                    Habbo kick = Emulator.getGameEnvironment().getHabboManager().getHabbo(params[1]);
                    if (!target.hasPermission(Permission.ACC_UNKICKABLE) && !target.hasPermission(Permission.ACC_SUPPORTTOOL) && !room.isOwner(target)) {
                        room.kickHabbo(kick, true);
                    }

                    //ALERTA
                    THashMap<String, String> keys = new THashMap<String, String>();
                    keys.put("display", "BUBBLE");
                    keys.put("image", "https://habbroi.biz/premiar/" + target.getHabboInfo().getLook() + ".png");
                    keys.put("USERNAME", target.getHabboInfo().getUsername());
                    Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("gameinvite", keys));
                } else {
                    gameClient.getHabbo().whisper(Emulator.getTexts().getValue("commands.erro.cmd_premiar.useroffline").replace("%user%", target.getHabboInfo().getUsername()), RoomChatMessageBubbles.ALERT);
                }

            }
        }


        return true;
    }
}
