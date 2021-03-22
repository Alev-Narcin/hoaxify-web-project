package com.hoaxify.ws.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "hoaxify")  //yaml dosyasındaki değişkenleri bu class a assaign etmek için kullanılıyor.
public class AppConfiguration {

    private String uploadPath;
}
