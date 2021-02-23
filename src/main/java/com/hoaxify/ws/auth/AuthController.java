package com.hoaxify.ws.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@CurrentUser User user) {    //required = false -> bu header olmasa da sen bizim methodumuza bu request i ulaştır.(header ın opsiyonel olduğunu söylüyoruz.yani oladabilir olmayadabilir) Sonrada içerde kendimiz kontrol ederiz " if " ile.
        return ResponseEntity.ok(user);  //ok içine direk inDB de koyabiliriz. Ancak görmek istemeklerimize JsonIgnore koymalıyız. böyle oluncada istek attığımızda bad request cevabını almış oluyoruz.
    }
    // BasicErrorController -> uygulama içerisinde yakalayamadığımız hataları spring kendisi yakalıyor ve bir cevaba dönüştürüyor.
}