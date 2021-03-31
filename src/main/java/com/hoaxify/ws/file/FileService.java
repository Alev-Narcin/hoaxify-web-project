package com.hoaxify.ws.file;

import com.hoaxify.ws.configuration.AppConfiguration;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    AppConfiguration appConfiguration;

    Tika tika;  //tika gönderdiğimiz dosya tipini detect(tespit etme) edebilme özelliğine sahip.

    public FileService(AppConfiguration appConfiguration) {
        super();
        this.appConfiguration = appConfiguration;
        this.tika = new Tika();
    }

    //private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public String writeBase64EncodedStringToFile(String image) throws IOException {  //profil resmini dosya olarak kaydettik.yukarıdaki method için kullanılan asıl dosya kaydetme-encode etme kısmı.
        String fileName = generateRandomName();
        File target = new File(appConfiguration.getUploadPath() + "/" + fileName);
        OutputStream outputStream = new FileOutputStream(target);

        byte[] base64encoded = Base64.getDecoder().decode(image);

        outputStream.write(base64encoded);
        outputStream.close();
        return fileName;
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replaceAll("-", " ");
    }

    //kullanıcı profilini değiştince eski image silmek için.
    public void deleteFile(String oldImage) {
        if (oldImage == null) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(appConfiguration.getUploadPath(), oldImage));  // silinecek image için path i alıyor ve o path üzerinden image ı siliyor.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String detectType(String value) {
        byte[] base64encoded = Base64.getDecoder().decode(value);
        return tika.detect(base64encoded);
    }
}
