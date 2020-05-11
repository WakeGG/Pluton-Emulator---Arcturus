package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.achievements.AchievementManager;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.permissions.Permission;
import com.eu.habbo.habbohotel.permissions.Rank;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import gnu.trove.map.hash.THashMap;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DarVIPCommand extends Command {
    public DarVIPCommand() {
        super("cmd_premiar", Emulator.getTexts().getValue("hybbe.cmd_darvip.keys").split(";"));
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


                if (target != null) {
                }

                // DA RANK
                Emulator.getGameEnvironment().getHabboManager().setRank(target.getHabboInfo().getId(), 2);
                // CONQUISTA
                AchievementManager.progressAchievement(target, Emulator.getGameEnvironment().getAchievementManager().getAchievement(Emulator.getConfig().getValue("premiar.conquista")));
                // BALANCE
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
                    vipa.put("message", "Você recebeu " + Emulator.getConfig().getInt("hybbe.premiar.rubis") + " rubis e " + Emulator.getConfig().getInt("hybbe.premiar.gemas") + " gemas");
                    vipa.put("USERNAME", target.getHabboInfo().getUsername());
                    Habbo vip = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(params[1]);
                    vip.getClient().sendResponse(new BubbleAlertComposer("event", vipa));
                }
                //INSERE
                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement premiar = connection.createStatement()) {
                    String sql = "UPDATE users SET vip_expira = ? WHERE username = ? ";
                    PreparedStatement pstmt = connection.prepareStatement(sql);
                    try {
                        pstmt.setString(1, params[2]);
                        pstmt.setString(2, target.getHabboInfo().getUsername());
                        pstmt.executeUpdate();
                    } finally {
                    }
                } catch (SQLException e) {
                }

                gameClient.getHabbo().getClient().getHabbo().whisper("Você deu vip ao usuário " + target.getHabboInfo().getUsername() + " com sucesso!", RoomChatMessageBubbles.ALERT);

                Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();


            }

        }
        return true;
    }
}
