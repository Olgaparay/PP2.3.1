package web.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.UserService;

import javax.validation.Valid;
import org.slf4j.Logger;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        logger.info("Запрос на главную страницу");
        model.addAttribute("users", userService.peopleCount());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        logger.info("Запрос на просмотр пользователя с id: {}", id);
        model.addAttribute("user", userService.userShow(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        logger.info("Запрос на страницу создания нового пользователя");
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        logger.info("Запрос на создание нового пользователя");
        if (bindingResult.hasErrors()) {
            logger.warn("Ошибки валидации при создании пользователя");
            return "new";
        }
        userService.saveUser(user);
        logger.info("Пользователь успешно создан");
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        logger.info("Запрос на редактирование пользователя с id: {}", id);
        model.addAttribute("user", userService.userShow(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id) {
        logger.info("Запрос на обновление пользователя с id: {}", id);
        if (bindingResult.hasErrors()) {
            logger.warn("Ошибки валидации при обновлении пользователя с id: {}", id);
            return "edit";
        }
        userService.updateUser(id, user);
        logger.info("Пользователь с id: {} успешно обновлен", id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        logger.info("Запрос на удаление пользователя с id: {}", id);
        userService.deleteUser(id);
        logger.info("Пользователь с id: {} успешно удален", id);
        return "redirect:/";
    }

}
