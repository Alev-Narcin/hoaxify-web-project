package com.hoaxify.ws.user;

import com.hoaxify.ws.error.NotFoundException;
import com.hoaxify.ws.file.FileService;
import com.hoaxify.ws.user.vm.UserUpdateVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class UserService {

    UserRepository userRepository;     // buraya @Autowired yazarakta injection yapılabilir.
    PasswordEncoder passwordEncoder;
    FileService fileService;

    //2 türlü injection var. constructor injection yapmak 2. si
    //bir class ta sadece br tane constructor varsa ek olarak @Autowired yazılmasına gerek yok. zaten ilk olarak const. çalışır.
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService = fileService;
    }

    public void save(User user) {
        //user objesini kullanarak gelen password ü encrypt ederiz.
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public Page<User> getUsers(Pageable page, User user) {
        if (user != null) {   //eğer login user varsa, o loginUser ın olmadığı bir listeyi dön demek.
            return userRepository.findByUsernameNot(user.getUsername(), page);
        }
        return userRepository.findAll(page);         //user ları pageable yapıyoruz
    }

    public User getByUsername(String username) {
        User inDB = userRepository.findByUsername(username);  //db den user ı aldık
        if (inDB == null) {
            throw new NotFoundException();
        }
        return inDB;
    }

    //profil resmini dosya olarak kaydettik.
    public User updateUser(String username, UserUpdateVM userUpdateVM) {
        User inDB = getByUsername(username);
        inDB.setDisplayName(userUpdateVM.getDisplayName());
        if (userUpdateVM.getImage() != null) {
            String oldImage = inDB.getImage();   //eski resmi silmek için
            try {
                String storedFileName = fileService.writeBase64EncodedStringToFile(userUpdateVM.getImage());
                inDB.setImage(storedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileService.deleteFile(oldImage);
        }
        return userRepository.save(inDB);
    }

}
