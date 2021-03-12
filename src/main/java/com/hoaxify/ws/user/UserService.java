package com.hoaxify.ws.user;

import com.hoaxify.ws.error.NotFoundException;
import com.hoaxify.ws.user.vm.UserUpdateVM;
import com.hoaxify.ws.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;     // buraya @Autowired yazarakta injection yapılabilir.
    PasswordEncoder passwordEncoder;

    @Autowired
    //2 türlü injection var. constructor injection yapmak 2. si
    //bir class ta sadece br tane constructor varsa ek olarak @Autowired yazılmasına gerek yok. zaten ilk olarak const. çalışır.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        //user objesini kullanarak gelen password ü encrypt ederiz.
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public Page<User> getUsers(Pageable page, User user) {
        if(user != null) {   //eğer login user varsa, o loginUser ın olmadığı bir listeyi dön demek.
            return userRepository.findByUsernameNot(user.getUsername(), page);
        }
        return userRepository.findAll(page);         //user ları pageable yapıyoruz
    }

    public User getByUsername(String username) {
        User inDB = userRepository.findByUsername(username);  //db den user ı aldık
        if(inDB==null) {
            throw new NotFoundException();
        }
        return inDB;
    }

    public User updateUser(String username, UserUpdateVM userUpdateVM) {
        User inDB = getByUsername(username);
        inDB.setDisplayName(userUpdateVM.getDisplayName());
        if(userUpdateVM.getImage() != null) {
            inDB.setImage(userUpdateVM.getImage());
        }
        return userRepository.save(inDB);
    }
}
