package ru.itmentor.spring.boot_security.demo.service;



import org.springframework.security.core.userdetails.UserDetails;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {


    User findUserById(Long id);

    List<User> findAllUsers();

    void deleteUserById(Long id);

    UserDetails loadUserByUsername(String username);

    boolean saveUser(User user);
    boolean updateUser(User user);

    Long getUsernameByName(String name);

    User getUserAndRole(User user, String[]roles);

    User getNotNullRole(User user);

    void addTestUsers();
}
