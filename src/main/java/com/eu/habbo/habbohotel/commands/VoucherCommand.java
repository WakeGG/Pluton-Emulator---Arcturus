package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomState;
import com.eu.habbo.messages.outgoing.generic.alerts.BubbleAlertComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomSettingsUpdatedComposer;
import gnu.trove.map.hash.THashMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import com.eu.habbo.util.RandomKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VoucherCommand extends Command {
    public VoucherCommand() {
        super("cmd_event", Emulator.getTexts().getValue("commands.keys.cmd_voucher").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        String codigo = RandomStringUtils.randomAlphanumeric(8);
            if (params.length >= 0) {
                if (params[1].equalsIgnoreCase("info")) {

                    String message = "Lista de comandos\r\n";
                    message = message + "" +
                            ":voucher moedas [quantidade] > Envia um voucher de moedas\r\n" +
                            ":voucher gemas [quantidade] > Envia um voucher de gemas\r\n" +
                            ":voucher rubis [quantidade] > Envia um voucher de rubis\r\n";
                gameClient.sendResponse(new ModToolIssueHandledComposer(message));
                }
            }
            if (params.length > 2) {
                if (params[1].equalsIgnoreCase("moedas")) {
                    String moedas = "";

                    for (int i = 2; i < params.length; ++i) {
                        moedas = moedas + params[i];
                        moedas = moedas + " ";
                    }

                    try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
                         PreparedStatement pstmt = connection.prepareStatement("INSERT INTO vouchers (code, credits) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS))
                    {
                        pstmt.setString(1, codigo);
                        pstmt.setString(2, moedas);
                        pstmt.execute();
                    } catch (SQLException e) {
                        Emulator.getLogging().logSQLException(e);
                    }

                    Emulator.getGameEnvironment().getCatalogManager().initialize();

                    THashMap<String, String> keys = new THashMap();
                    keys.put("display", "BUBBLE");
                    keys.put("image", "${image.library.url}notifications/voucher.png");
                    keys.put("message", "Um novo código voucher foi liberado, gere o código na loja e ganhe premios:\n\n" + codigo);
                    Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
                }
            }

        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("gemas")) {
                String gemas = "";

                for (int i = 2; i < params.length; ++i) {
                    gemas = gemas + params[i];
                    gemas = gemas + " ";
                }

                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
                     PreparedStatement pstmt = connection.prepareStatement("INSERT INTO vouchers (code, points) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS))
                {
                    pstmt.setString(1, codigo);
                    pstmt.setString(2, gemas);
                    pstmt.execute();
                } catch (SQLException e) {
                    Emulator.getLogging().logSQLException(e);
                }

                Emulator.getGameEnvironment().getCatalogManager().initialize();

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/voucher.png");
                keys.put("message", "Um novo código voucher foi liberado, gere o código na loja e ganhe premios:\n\n" + codigo);
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }

        if (params.length > 2) {
            if (params[1].equalsIgnoreCase("rubis")) {
                String rubis = "";

                for (int i = 2; i < params.length; ++i) {
                    rubis = rubis + params[i];
                    rubis = rubis + " ";
                }

                try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
                     PreparedStatement pstmt = connection.prepareStatement("INSERT INTO vouchers (code, points, points_type) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
                {
                    pstmt.setString(1, codigo);
                    pstmt.setString(2, rubis);
                    pstmt.setString(3, "5");
                    pstmt.execute();
                } catch (SQLException e) {
                    Emulator.getLogging().logSQLException(e);
                }

                Emulator.getGameEnvironment().getCatalogManager().initialize();

                THashMap<String, String> keys = new THashMap();
                keys.put("display", "BUBBLE");
                keys.put("image", "${image.library.url}notifications/voucher.png");
                keys.put("message", "Um novo código voucher foi liberado, gere o código na loja e ganhe premios:\n\n" + codigo);
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new BubbleAlertComposer("", keys));
            }
        }

        return true;
    }

}
