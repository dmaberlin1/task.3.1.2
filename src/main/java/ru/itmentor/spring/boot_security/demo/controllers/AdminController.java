package ru.itmentor.spring.boot_security.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleServiceImpl;
import ru.itmentor.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;


/**
 * @GetMapping("/admin"): Отображает страницу администратора со списком всех пользователей.
 * @GetMapping("/admin/user-save"): Отображает форму для создания нового пользователя администратором.
 * @PostMapping("/admin/user-save"): Обрабатывает POST-запрос для сохранения нового пользователя администратором.
 * @DeleteMapping("/admin/user-delete/{id}"): Обрабатывает DELETE-запрос для удаления пользователя администратором.
 * @GetMapping("/admin/user-update/{id}"): Отображает форму для обновления информации о пользователе администратором.
 * @PostMapping("/admin/user-update"): Обрабатывает POST-запрос для обновления информации о пользователе администратором.
 */

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    /**
     * Отображает страницу администратора со списком всех пользователей.
     *
     * @param model Модель для передачи данных в представление.
     * @return Строка, представляющая имя представления "admin".
     */
    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> user = userService.findAllUsers();
        model.addAttribute("allUser", user);
        return "admin";
    }


    /**
     * Отображает форму для создания нового пользователя администратором.
     *
     * @param model Модель для передачи данных в представление.
     * @return Строка, представляющая имя представления "admin-save".
     */
    @GetMapping("/admin/user-save")
    public String saveUserForm(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "admin-save";
    }

    /**
     * Обрабатывает POST-запрос для сохранения нового пользователя администратором.
     *
     * @param user Новый пользователь, переданный из формы.
     * @return Строка перенаправления на страницу администратора после успешного сохранения.
     */

    @PostMapping("/admin/user-save")
    public String saveUser(User user) {
        userService.getNotNullRole(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    /**
     * Обрабатывает DELETE-запрос для удаления пользователя администратором.
     *
     * @param id Идентификатор пользователя, который будет удален.
     * @return Строка перенаправления на страницу администратора после успешного удаления.
     */
    @DeleteMapping("admin/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    /**
     * Отображает форму для обновления информации о пользователе администратором.
     *
     * @param id    Идентификатор пользователя, информацию о котором нужно обновить.
     * @param model Модель для передачи данных в представление.
     * @return Строка, представляющая имя представления "admin-update".
     */
    @GetMapping("/admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id")Long id,Model model){
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("user",userService.findUserById(id));
        return "admin-update";
    }
    /**
     * Обрабатывает POST-запрос для обновления информации о пользователе администратором.
     *
     * @param user  Обновленная информация о пользователе.
     * @param role Роли пользователя, переданные из формы.
     * @return Строка перенаправления на страницу администратора после успешного обновления.
     */
    @PostMapping("/admin/user-update")
    public String updateUsers(@ModelAttribute("user")User user,@RequestParam(value = "nameRole",required = false)String[]role){
        userService.getUserAndRole(user,role);
        userService.updateUser(user);
        return "redirect:/admin";
    }

}
