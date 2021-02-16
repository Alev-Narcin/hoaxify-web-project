package com.hoaxify.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class UserService {

    // buraya @Autowired yazarakta injection yapılabilir.
    UserRepository userRepository;

    @Autowired //2 türlü injection var. constructor injection yapmak 2. si
    // bir class ta sadece br tane constructor varsa ek olarak @Autowired yazılmasına gerek yok. zaten ilk olarak const. çalışır.

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void save(User user) {
        userRepository.save(user);
    }

}
