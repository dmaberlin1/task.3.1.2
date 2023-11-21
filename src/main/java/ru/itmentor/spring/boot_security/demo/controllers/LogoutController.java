package ru.itmentor.spring.boot_security.demo.controllers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {

    /**
     * Обрабатывает POST-запрос для выхода из системы.
     *
     * @param request  Объект запроса HTTP.
     * @param response Объект ответа HTTP.
     * @return Строка перенаправления на страницу входа после успешного выхода.
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        // Получаем текущий аутентифицированный контекст безопасности
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь
        if(authentication!=null){
            // Если пользователь аутентифицирован, выполняем выход из системы
            //Это включает в себя очистку аутентификационных данных и инвалидацию текущей сессии.
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        // После успешного выхода, перенаправляем пользователя на страницу входа
        return "redirect:/login";
    }
}
