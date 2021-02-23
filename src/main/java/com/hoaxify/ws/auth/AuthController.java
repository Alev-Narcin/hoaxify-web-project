package com.hoaxify.ws.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;


@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization") String authorization) {    //required = false -> bu header olmasa da sen bizim methodumuza bu request i ulaştır.(header ın opsiyonel olduğunu söylüyoruz.yani oladabilir olmayadabilir) Sonrada içerde kendimiz kontrol ederiz " if " ile.
        String base64encoded = authorization.split("Basic ")[1];   // "Basic dXNlcjY2OnBhYQ==" sadece şifre kısmını getirmek için split fonk. kullanıyoruz. Yani sonuçta elimizde "dXNlcjY2OnBhYQ==" password ü kalmış oluyor.
        String decoded = new String(Base64.getDecoder().decode(base64encoded));   //dönen cevap bi byte array bu yüzden string e dönüştürüyoruz. // user1:Pass4word! elimizde
        String[] parts = decoded.split(":");  //["user1", "P4ssword!"]
        String username = parts[0];
        User inDB = userRepository.findByUsername(username);

        return ResponseEntity.ok(inDB);  //ok içine direk inDB de koyabiliriz. Ancak görmek istemeklerimize JsonIgnore koymalıyız. böyle oluncada istek attığımızda bad request cevabını almış oluyoruz.
    }

    // BasicErrorController uygulama içerisinde yakalayamadığımız hataları spring kendisi yakalıyor ve bir cevaba dönüştürüyor.
}