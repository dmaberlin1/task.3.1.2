package ru.itmentor.spring.boot_security.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Gender;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;


    @Override
    public User findUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElse(new User());
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with this " + username + " User Name not found");
        }
        return user;
    }

    @Override
    public boolean saveUser(User user) {
        User byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername != null) {
            return false;
        }
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /*   В данном случае, метод saveUserTest предназначен для тестирования и добавления пользователей в систему
           Использование отдельного метода для тестирования может быть полезным, поскольку это позволяет добавить
        или изменить логику только для тестового окружения, не затрагивая основной код, который используется в продакшн.
            */
    public boolean saveUserTest(User user) {
        User byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername != null) {
            return false;
        }
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public Long getUsernameByName(String name) {
        User user = userRepository.findByUsername(name);
        return user.getId();
    }

    /**
     * Получает пользователя для обновления ролей.
     *
     * @param user     Объект пользователя, который будет обновлен.
     * @param username Имя пользователя, для которого выполняется обновление.
     * @return Обновленный объект пользователя с актуальными ролями из базы данных.
     */
    public User getUserForUpdateUsers(User user, String username) {
        // Шаг 1: Получение идентификатора пользователя по его имени
        // Шаг 2: Получение пользователя из базы данных по идентификатору
        User userById = findUserById(getUsernameByName(username));
        // Шаг 3: Извлечение ролей пользователя из базы данных
        Set<Role> role = userById.getRole();

        // Шаг 4: Обновление ролей в объекте пользователя (user)
        user.setRole(
                // Преобразование имен ролей в массив строк
                roleService.getRoleByName(role.stream()
                .map(role1 -> role1.getName())
                .toArray(String[]::new)));
        // Шаг 5: Возврат обновленного объекта пользователя
        return user;
    }

    @Override
    public User getUserAndRole(User user, String[] role) {
        if (role == null) {
            // Если равен null, устанавливаем для пользователя роль "ROLE_USER"
            user.setRole(roleService.getRoleByName(new String[]{"ROLE_USER"}));
        } else {
            // Если массив ролей не равен null, устанавливаем для пользователя роли, полученные из сервиса
            user.setRole(roleService.getRoleByName(role));
        }
        // Возвращаем пользователя с установленными ролями
        return user;
    }

    @Override
    public User getNotNullRole(User user) {
        if (user.getRole() == null) {
            user.setRole(Collections.singleton(new Role(2L)));
        }
        return user;
    }

    @PostConstruct
    @Override
    public void addTestUsers() {
        roleRepository.save(new Role("ROLE_ADMIN", 1L));
        roleRepository.save(new Role("ROLE_USER", 2L));
        User newAdmin = new User("admin", "test", "admin@gmail.com", "admin111", Gender.PREFER_NOT_TO_SAY);
        saveUserTest(newAdmin);
        User newUser = new User("user", "userLastName", "user@gmail.com", "user111", Gender.MALE);
        saveUser(newUser);
    }
}
