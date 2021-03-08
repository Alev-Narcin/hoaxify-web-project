package com.hoaxify.ws.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/1.0/auth")
    UserVM handleAuthentication(@CurrentUser User user) {    //required = false -> bu header olmasa da sen bizim methodumuza bu request i ulaştır.(header ın opsiyonel olduğunu söylüyoruz.yani oladabilir olmayadabilir) Sonrada içerde kendimiz kontrol ederiz " if " ile.
        return new UserVM(user);
    }
    // BasicErrorController -> uygulama içerisinde yakalayamadığımız hataları spring kendisi yakalıyor ve bir cevaba dönüştürüyor.
}