package com.hoaxify.ws.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Page<User> findByUsernameNot(String username, Pageable page);   //o anki user ı ana sayfada göstermemesi için

//    @Query(value = "select u from User u")  //JPQL = java persistance query language
//    //query sonucunda buradaki User(UserProjection barındıran bir User objesi) objesini dönecek.
//    Page<UserProjection> getAllUsersProjection(Pageable page);
}
