package ru.itmentor.spring.boot_security.demo.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


/**Principal - это интерфейс в Java, представляющий текущего аутентифицированного пользователя в системе.
 *  Он предоставляет метод getName(), который возвращает имя текущего пользователя.
 *  В контексте веб-приложений с использованием Spring Security, объект Principal содержит информацию
 *  о текущем аутентифицированном пользователе.

 В контексте кода, Principal principal в методах контроллера используется для получения информации о текущем
 аутентифицированном пользователе. Например, в методе showUser он используется для получения информации о пользователе,
 чтобы отобразить его данные.
*/

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/user")
    public String showUser(Principal principal,Model model){
        User user = (User) userService.loadUserByUsername(principal.getName());
        model.addAttribute("oneUser",user);
        return "/user";
    }


    /**
     * Отображает форму обновления пользователя.
     *
     * @param id    Идентификатор пользователя, которого нужно обновить.
     * @param model Модель для передачи данных в представление.
     * @return Путь к представлению с формой обновления пользователя.
     */
    @GetMapping("/user/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id,Model model){
        model.addAttribute("user",userService.findUserById(id));
        return "user-update";
    }

    /**
     * Обрабатывает запрос на обновление пользователя.
     *
     * @param user      Объект пользователя с обновленными данными.
     * @param principal Аутентифицированный пользователь.
     * @return Путь к представлению информации о пользователе (редирект).
     */
    @PostMapping("/user/user-update")
    public String updateUsers(@ModelAttribute("user")User user,Principal principal){
        userService.getUserForUpdateUsers(user,principal.getName());
        userService.updateUser(user);
        return "redirect:/user";
    }

    @GetMapping("/news")
    public String showNews(){
        return "/news";
    }
}
