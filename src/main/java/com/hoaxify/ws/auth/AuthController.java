package com.hoaxify.ws.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/api/1.0/auth")
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization) {    //required = false -> bu header olmasa da sen bizim methodumuza bu request i ulaştır.(header ın opsiyonel olduğunu söylüyoruz.yani oladabilir olmayadabilir) Sonrada içerde kendimiz kontrol ederiz " if " ile.

        if(authorization == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info(authorization);
        return ResponseEntity.ok().build();
    }
}
