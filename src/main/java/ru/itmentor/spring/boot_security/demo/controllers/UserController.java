package ru.itmentor.spring.boot_security.demo.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping("/user")
    public String showAllUsers(Model model, @ModelAttribute("flashMessage") String flashAttribute){
        model.addAttribute("users",userService.getAllUsers());
        return "index";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "show";
    }

    @GetMapping(value = "/new")
    public String addUserForm(@ModelAttribute("user")User user){
        return "new";
    }



    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable(value = "id",required = true) int id, Model model, RedirectAttributes attributes){
        User user = userService.getUserById(id);
        if(user==null){
            attributes.addFlashAttribute("flashMessage","User are not exists!");
            return "redirect:/users";
        }
        model.addAttribute("user",userService.getUserById(id));
        return "edit";
    }

    @RequestMapping("/{id}")
    public String saveUser(@ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes attributes){
        if(bindingResult.hasErrors()){
            return "new";
        }
        userService.createOrUpdateUser(user);
        attributes.addFlashAttribute("flashMessage","User "+user.getFirstName()+ " successfully created!");
        return "redirect:/users";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id",required = true,defaultValue = "")int id, RedirectAttributes attributes){
        User user=userService.deleteUser(id);

        attributes.addFlashAttribute("flashMessage",(user==null)
                ? "User are not exists!"
                : "User "+user.getFirstName()+ " successfully deleted");
        return "redirect:/users";
    }



}
