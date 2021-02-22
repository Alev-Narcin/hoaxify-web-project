package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import com.hoaxify.ws.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
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
    CommandLineRunner createInitialUsers(UserService userService) {
        return (args) -> {

            User user = new User();
            user.setUsername("user1");
            user.setDisplayName("display1");
            user.setPassword("P4ssword!");
            userService.save(user);
        };
    }
}

