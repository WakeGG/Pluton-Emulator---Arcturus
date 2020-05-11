package com.eu.habbo.messages.incoming.camera;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.messages.incoming.MessageHandler;
import com.eu.habbo.messages.outgoing.camera.CameraRoomThumbnailSavedComposer;
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

public class CameraRoomThumbnailEvent extends MessageHandler {
    public CameraRoomThumbnailEvent() {
    }

    public void handle() {
        if (!this.client.getHabbo().hasPermission("acc_camera")) {
            this.client.sendResponse(new GenericAlertComposer(Emulator.getTexts().getValue("camera.permission")));
        } else if (this.client.getHabbo().getHabboInfo().getCurrentRoom().isOwner(this.client.getHabbo())) {
            Room room = this.client.getHabbo().getHabboInfo().getCurrentRoom();
            if (room != null) {
                if (room.isOwner(this.client.getHabbo()) || this.client.getHabbo().hasPermission("acc_modtool_ticket_q")) {
                    int count = this.packet.readInt();
                    ByteBuf image = this.packet.getBuffer().readBytes(count);
                    if (image != null) {
                        this.packet.readString();
                        this.packet.readString();
                        this.packet.readInt();
                        this.packet.readInt();

                        try {
                            if (Emulator.getConfig().getInt("ftp.enabled") == 1) {
                                byte[] imageBytes = new byte[image.readableBytes()];
                                image.readBytes(imageBytes);
                                FTPUploadService.uploadImage(imageBytes, Emulator.getConfig().getValue("imager.location.output.thumbnail") + room.getId() + ".png");
                            } else {
                                BufferedImage theImage = null;
                                theImage = ImageIO.read(new ByteBufInputStream(image));
                                ImageIO.write(theImage, "png", new File(Emulator.getConfig().getValue("imager.location.output.thumbnail") + room.getId() + ".png"));
                            }
                        } catch (IOException var5) {
                            var5.printStackTrace();
                        } catch (IllegalArgumentException var6) {
                            System.out.println("[Apollyon] You are using a Habbo.swf that has not been patched to work with Apollyon. Please read the read me on a guide to patching your swf, or download a prepatched one on our git at:");
                            System.out.println("[Apollyon] https://git.krews.org/morningstar/apollyon");
                        }

                        this.client.sendResponse(new CameraRoomThumbnailSavedComposer());
                    }
                }
            }
        }
    }
}
