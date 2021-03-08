package com.hoaxify.ws.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericResponse;
import com.hoaxify.ws.shared.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/1.0/users")
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }

    @GetMapping("/api/1.0/users")
//    @JsonView(Views.Base.class)  //bu methodu JsonView olarak oluştur anlamında.
    Page<User> getUsers(Pageable page ) {
        return userService.getUsers(page);
    }

}
