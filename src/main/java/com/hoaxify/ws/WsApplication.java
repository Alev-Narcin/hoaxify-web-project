package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
//(exclude = SecurityAutoConfiguration.class)   //security eklenmediği zaman kullanılır(en başta kullandık)
public class WsApplication {

    public static void main(String[] args) {

        SpringApplication.run(WsApplication.class, args);
    }

    //Kullanıcı oluşturmak için bu methodu kullanıyoruz.
    //Spring in bu fonks.kullanabilmesi için @Bean annotation' ını ekliyoruz.
    //Spring application ı ayağa kalkarken CommendLineRunner tipinde bir bean görünce onun run methodunu çağırıyor.
    //Böylece uygulama ayağa kalktıktan sonra (initialize anında) koşabildiğimiz bir komut alanı oluşmuş oluyor.
    //CommendLineRunner bir interface dir.
    @Bean
    @Profile("dev")  //uygulamanın sadece dev ortamında çalışması için kullanılan annotation.
    CommandLineRunner createInitialUsers(UserService userService) {
        return (args) -> {
            for(int i=1; i<=25; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setDisplayName("display" + i);
                user.setPassword("P4ssword!");
                userService.save(user);
            }

        };
    }
}

