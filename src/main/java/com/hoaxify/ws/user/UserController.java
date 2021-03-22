package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.CurrentUser;
import com.hoaxify.ws.shared.GenericResponse;
import com.hoaxify.ws.user.vm.UserUpdateVM;
import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }

    @GetMapping("/users")
    Page<UserVM> getUsers(Pageable page, @CurrentUser User user) {
        return userService.getUsers(page, user).map(UserVM::new);  //user dan UserVM oluşturuldu
    }

    @GetMapping("/users/{username}")
    UserVM getUser(@PathVariable String username) {     //@PathVariable: url e verdiğimiz değişken için kullanılan annotation
        User user = userService.getByUsername(username);   //controller db den alınan user ı UserVM e dönüştürüp return ediyor.
        return new UserVM(user);
    }

    @PutMapping("/users/{username}")
    //controllerda eklediğimiz annotation sayesinde(@EnableGlobalMethodSecurity(prePostEnabled = true)) spring security bu methoda girmeden authorization var mı yok mu ona bakıyor.
    //eğer buna match etmiyorsa client bu method için 403 atıcak.
    @PreAuthorize("#username == principal.username")
    UserVM updateUser(@Valid @RequestBody UserUpdateVM updatedUser, @PathVariable String username) {  //ResponseEntity : hem UserVM hemde ApiError dönebilmemiz için onu bu şekilde sarıyoruz.
        User user = userService.updateUser(username, updatedUser);
        return new UserVM(user);
    }
}
