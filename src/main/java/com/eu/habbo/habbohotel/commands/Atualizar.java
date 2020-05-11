package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.messages.outgoing.catalog.*;
import com.eu.habbo.messages.outgoing.catalog.marketplace.MarketplaceConfigComposer;
import com.eu.habbo.messages.outgoing.modtool.ModToolIssueHandledComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomRelativeMapComposer;

public class Atualizar extends Command {
    public Atualizar() {
        super("cmd_update_achievements", Emulator.getTexts().getValue("commands.keys.cmd_update").split(";"));
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {

        if (params.length < 2) {
                String message = "Lista de comandos\r\n";
                message = message + "" +
                        ":atualizar conquistas - \r\n" +
                        ":atualizar bots - \r\n" +
                        ":atualizar cata - \r\n" +
                        ":atualizar items - \r\n" +
                        ":atualizar navi - \r\n" +
                        ":atualizar config - \r\n" +
                        ":atualizar textos - \r\n" +
                        ":atualizar pets - \r\n" +
                        ":atualizar grupos - \r\n" +
                        ":atualizar polls - \r\n" +
                        ":atualizar filtro - \r\n" +
                        ":atualizar perms - \r\n" +
                        ":atualizar youtube -" +
                        "\r\n";
                gameClient.sendResponse(new ModToolIssueHandledComposer(message));
            return true;
        }


        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("conquistas")) {

                Emulator.getGameEnvironment().getAchievementManager().reload();
                gameClient.getHabbo().whisper("Conquistas atualizadas", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("bots")) {

                Emulator.getGameEnvironment().getBotManager().reload();
                gameClient.getHabbo().whisper("Bots atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("cata")) {

                Emulator.getGameEnvironment().getCatalogManager().initialize();
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new CatalogUpdatedComposer());
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new CatalogModeComposer(0));
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new DiscountComposer());
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new MarketplaceConfigComposer());
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new GiftConfigurationComposer());
                Emulator.getGameServer().getGameClientManager().sendBroadcastResponse(new RecyclerLogicComposer());
                Emulator.getGameEnvironment().getCraftingManager().reload();
                gameClient.getHabbo().whisper("Catalogo atualizado", RoomChatMessageBubbles.ALERT);

            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("config")) {

                Emulator.getConfig().reload();
                gameClient.getHabbo().whisper("Configs atualizadas", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("grupos")) {

                Emulator.getGameEnvironment().getGuildManager().loadGuildParts();
                Emulator.getBadgeImager().reload();
                gameClient.getHabbo().whisper("Grupos atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("view")) {

                Emulator.getGameEnvironment().getHotelViewManager().getNewsList().reload();
                Emulator.getGameEnvironment().getHotelViewManager().getHallOfFame().reload();
                gameClient.getHabbo().whisper("HotelView atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("items")) {

                Emulator.getGameEnvironment().getItemManager().loadItems();
                Emulator.getGameEnvironment().getItemManager().loadCrackable();
                Emulator.getGameEnvironment().getItemManager().loadSoundTracks();

                synchronized (Emulator.getGameEnvironment().getRoomManager().getActiveRooms()) {
                    for (Room room : Emulator.getGameEnvironment().getRoomManager().getActiveRooms()) {
                        if (room.isLoaded() && room.getUserCount() > 0 && room.getLayout() != null) {
                            room.sendComposer(new RoomRelativeMapComposer(room).compose());
                        }
                    }
                }
                gameClient.getHabbo().whisper("Items atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("navi")) {

                Emulator.getGameEnvironment().getNavigatorManager().loadNavigator();
                Emulator.getGameEnvironment().getRoomManager().loadRoomModels();
                gameClient.getHabbo().whisper("Navegador atualizado", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("perms")) {

                Emulator.getGameEnvironment().getPermissionsManager().reload();
                gameClient.getHabbo().whisper("Permissoes atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("pets")) {

                Emulator.getGameEnvironment().getPetManager().reloadPetData();
                gameClient.getHabbo().whisper("Mascotes atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("polls")) {

                Emulator.getGameEnvironment().getPollManager().loadPolls();
                gameClient.getHabbo().whisper("Polls atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("textos")) {

                try {
                    Emulator.getTexts().reload();
                    Emulator.getGameEnvironment().getCommandHandler().reloadCommands();
                    gameClient.getHabbo().whisper("Textos atualizados", RoomChatMessageBubbles.ALERT);
                } catch (Exception e) {
                    gameClient.getHabbo().whisper("Algo de errados nos textos, confira e atualize de novo", RoomChatMessageBubbles.ALERT);
                }

            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("filtro")) {

               // Emulator.getGameEnvironment().getWordFilter().reload();
                gameClient.getHabbo().whisper("Filtro atualizados", RoomChatMessageBubbles.ALERT);
            }
        }

        if (params.length > 1) {
            if (params[1].equalsIgnoreCase("youtube")) {

                Emulator.getGameEnvironment().getItemManager().getYoutubeManager().load();
                gameClient.getHabbo().whisper("Youtube atualizado", RoomChatMessageBubbles.ALERT);
            }
        }

        return true;
    }
}
