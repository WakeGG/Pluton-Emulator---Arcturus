package com.eu.habbo.messages.incoming.camera;

import com.eu.habbo.Emulator;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FTPUploadService {
    private static final String ftpUrl = "ftp://%s:%s@%s/%s;type=i";

    public FTPUploadService() {
    }

    public static void uploadImage(byte[] image, String uploadPath) throws IOException {
        String host = Emulator.getConfig().getValue("ftp.host");
        String user = Emulator.getConfig().getValue("ftp.user");
        String pass = Emulator.getConfig().getValue("ftp.password");
        String uploadURL = String.format("ftp://%s:%s@%s/%s;type=i", user, pass, host, uploadPath);
        URL url = new URL(uploadURL);
        URLConnection conn = url.openConnection();
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(image, 0, image.length);
        outputStream.close();
    }
}

