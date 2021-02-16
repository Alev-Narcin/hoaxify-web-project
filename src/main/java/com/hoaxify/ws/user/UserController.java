package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    //private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    @ResponseStatus(HttpStatus.OK)  //response mesajı için istenilen cevap döner.
    public GenericResponse createUser(@RequestBody User user) {
        //log.info(user.toString());
        userService.save(user);

        return new GenericResponse("user created"); //@AllArgsConstructor eklersek buraya parametre eklemek gerekir.
    }
}
