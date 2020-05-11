package com.eu.habbo.habbohotel.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.incoming.MessageHandler;

public class InfoMobisCommand extends Command {
    public InfoMobisCommand() {
        super("cmd_moonwalk", Emulator.getTexts().getValue("commands.keys.cmd_furnidataaa").split(";"));
    }


    public boolean handle(GameClient gameClient, String[] params, MessageHandler messageHandler) throws Exception {
        Room room = messageHandler.client.getHabbo().getHabboInfo().getCurrentRoom();
        if (room != null) {
            int itemId = messageHandler.packet.readInt();
            int state = messageHandler.packet.readInt();
            HabboItem item = room.getHabboItem(itemId);
            String message = "Information for item: <b>" + item.getBaseItem().getFullName() + "</b>\r\n<b>items_base table</b>\r\n- id: " + item.getBaseItem().getId() + "\r- sprite_id: " + item.getBaseItem().getSpriteId() + "\r- Width:  " + item.getBaseItem().getWidth() + "\r- Length:  " + item.getBaseItem().getWidth() + "\r- Stack height:  " + item.getBaseItem().getHeight() + "\r- Allow stack:  " + item.getBaseItem().allowStack() + "\r- Allow walk:  " + item.getBaseItem().allowStack() + "\r- Allow sit:  " + item.getBaseItem().allowStack() + "\r- Allow lay:  " + item.getBaseItem().allowStack() + "\r- Allow recycle:  " + item.getBaseItem().allowStack() + "\r- Allow trade:  " + item.getBaseItem().allowStack() + "\r- Allow marketplace sell:  " + item.getBaseItem().allowStack() + "\r- Allow gift:  " + item.getBaseItem().allowStack() + "\r- Allow inventory stack:  " + item.getBaseItem().allowStack() + "\r- Interaction type:  " + item.getBaseItem().getInteractionType().getName() + "\r- Interaction count:  " + item.getBaseItem().getStateCount() + "\r- Vending ids:  ";

            int i;
            for(i = 0; i < item.getBaseItem().getVendingItems().size(); ++i) {
                message = message + item.getBaseItem().getVendingItems().get(i) + ", ";
            }

            message = message.substring(0, message.length() - 2);
            message = message + "\r- effect id male:  " + item.getBaseItem().getEffectM() + "\r- effect id female:  " + item.getBaseItem().getEffectF() + "\r- Multi height:  ";

            for(i = 0; i < item.getBaseItem().getMultiHeights().length; ++i) {
                message = message + item.getBaseItem().getMultiHeights()[i] + ", ";
            }

            message = message.substring(0, message.length() - 2);
            message = message + "\r\n<b>items/room</b>\r\n- item id: " + item.getId() + "\r- user id: " + item.getUserId() + "\r- x:  " + item.getX() + "\r- y:  " + item.getY() + "\r- z:  " + item.getZ() + "\r- State:  " + state + "\r";
            messageHandler.client.getHabbo().alert(message);
        }

        return false;
    }

    @Override
    public boolean handle(GameClient gameClient, String[] params) throws Exception {
        return false;
    }
}
