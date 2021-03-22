package com.hoaxify.ws.file;

import com.hoaxify.ws.configuration.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    AppConfiguration appConfiguration;

    //profil resmini dosya olarak kaydettik.yukarıdaki method için kullanılan asıl dosya kaydetme-encode etme kısmı.
    public String writeBase64EncodedStringToFile(String image) throws IOException {
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getUploadPath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64Encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64Encoded);
        outputStream.close();
        return fileName;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", " ");
    }

    //kullanıcı profilini değiştince eski image silmek için.
    public void deleteFile(String oldImage) {
        if(oldImage == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImage));  // silinecek image için path i alıyor ve o path üzerinden image ı siliyor.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
