package com.eu.habbo.messages.incoming.camera;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.catalog.CatalogManager;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.camera.CameraURLComposer;
import com.eu.habbo.messages.outgoing.generic.alerts.GenericAlertComposer;
import com.eu.habbo.networking.camera.CameraClient;
import com.eu.habbo.networking.camera.messages.outgoing.CameraRenderImageComposer;
import com.eu.habbo.util.crypto.ZIP;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CameraRoomPictureEvent extends MessageHandler {
    @Override
    public void handle() {
        if ((long)Emulator.getIntUnixTimestamp() - this.client.getHabbo().getHabboStats().lastPurchaseTimestamp >= (long) CatalogManager.PURCHASE_COOLDOWN) {
            this.client.getHabbo().getHabboStats().lastPurchaseTimestamp = (long)Emulator.getIntUnixTimestamp();
            if (!this.client.getHabbo().hasPermission("acc_camera")) {
                this.client.sendResponse(new GenericAlertComposer(Emulator.getTexts().getValue("camera.permission")));
                return;
            }

            Room room = this.client.getHabbo().getHabboInfo().getCurrentRoom();
            if (room == null)
                return;


            final int count = this.packet.readInt();
            ByteBuf image = this.packet.getBuffer().readBytes(count);

            if (image == null)
                return;


            this.packet.readString();
            this.packet.readString();
            this.packet.readInt();
            this.packet.readInt();
            int timestamp = Emulator.getIntUnixTimestamp();
            String URL = this.client.getHabbo().getHabboInfo().getId() + "_" + timestamp + ".png";
            String URL_small = this.client.getHabbo().getHabboInfo().getId() + "_" + timestamp + "_small.png";
            String base = Emulator.getConfig().getValue("camera.url");
            String json = Emulator.getConfig().getValue("camera.extradata").replace("%timestamp%", timestamp + "").replace("%room_id%", room.getId() + "").replace("%username%", this.client.getHabbo().getHabboInfo().getUsername() + "").replace("%url%", base + URL);

            //String json = Emulator.getConfig().getValue("camera.extradata").replace("%timestamp%", timestamp + "").replace("%room_id%", room.getId() + "").replace("%url%", base + URL);
            this.client.getHabbo().getHabboInfo().setPhotoURL(base + URL);
            this.client.getHabbo().getHabboInfo().setPhotoTimestamp(timestamp);
            this.client.getHabbo().getHabboInfo().setPhotoUsername(this.client.getHabbo().getHabboInfo().getUsername());
            this.client.getHabbo().getHabboInfo().setPhotoRoomId(room.getId());
            this.client.getHabbo().getHabboInfo().setPhotoJSON(json);

            try {
                if (Emulator.getConfig().getInt("ftp.enabled") == 1) {
                    byte[] imageBytes = new byte[image.readableBytes()];
                    image.readBytes(imageBytes);
                    FTPUploadService.uploadImage(imageBytes, Emulator.getConfig().getValue("imager.location.output.camera") + URL);
                    FTPUploadService.uploadImage(imageBytes, Emulator.getConfig().getValue("imager.location.output.camera") + URL_small);
                } else {
                    BufferedImage theImage = ImageIO.read(new ByteBufInputStream(image));
                    ImageIO.write(theImage, "png", new File(Emulator.getConfig().getValue("imager.location.output.camera") + URL));
                    ImageIO.write(theImage, "png", new File(Emulator.getConfig().getValue("imager.location.output.camera") + URL_small));
                }
            } catch (IOException var10) {
                var10.printStackTrace();
            } catch (IllegalArgumentException var11) {
            }

            this.client.sendResponse(new CameraURLComposer(URL));
        }

    }
}
