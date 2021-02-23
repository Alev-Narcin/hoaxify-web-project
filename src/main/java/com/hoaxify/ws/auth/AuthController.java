package com.hoaxify.ws.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.Views;
import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization) {    //required = false -> bu header olmasa da sen bizim methodumuza bu request i ulaştır.(header ın opsiyonel olduğunu söylüyoruz.yani oladabilir olmayadabilir) Sonrada içerde kendimiz kontrol ederiz " if " ile.

        if(authorization == null) {
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        String base64encoded = authorization.split("Basic ")[1];   // "Basic dXNlcjY2OnBhYQ==" sadece şifre kısmını getirmek için split fonk. kullanıyoruz. Yani sonuçta elimizde "dXNlcjY2OnBhYQ==" password ü kalmış oluyor.
        String decoded = new String(Base64.getDecoder().decode(base64encoded));   //dönen cevap bi byte array bu yüzden string e dönüştürüyoruz. // user1:Pass4word! elimizde
        String[] parts = decoded.split(":");  //["user1", "P4ssword!"]
        String username = parts[0];
        String password = parts[1];
        User inDB = userRepository.findByUsername(username);

        //kullanıcının db de varlığını kontrol ettik
        if(inDB == null) {
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        //şifre doğruluğunu kontrol ettik
        String hashedPassword = inDB.getPassword();
        if(!passwordEncoder.matches(password, hashedPassword)) {  //BCrypt te password aynı olsa bile her seferinde faklı hash lendiği için decode edilemez. bu yüzden güvenli. onun yerine passwordEncoder ın matches fonk. kullanarak aynı olup olmadığını kontrol edebiliriz.
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        //username, displayName, image
        Map<String, String> responseBody = new HashMap<>();  //(key-value) obje tipi
        responseBody.put("username", inDB.getUsername());
        responseBody.put("displayName", inDB.getDisplayName());
        responseBody.put("image", inDB.getImage());

        return ResponseEntity.ok(responseBody);  //ok içine direk inDB de koyabiliriz. Ancak görmek istemeklerimize JsonIgnore koymalıyız. böyle oluncada istek attığımızda bad request cevabını almış oluyoruz.
    }
}
