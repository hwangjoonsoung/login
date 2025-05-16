package me.hwangjoonsoung.pefint.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.customException.InvalidUserFormException;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
import me.hwangjoonsoung.pefint.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final View error;

    @GetMapping("/alluser")
    public String getAllUser(Model model){
        List<User> allUser = userService.getAllUser();
        System.out.println(allUser);
        model.addAttribute("users", allUser);
        return "allusers";
    }

    @GetMapping("/new")
    public String gotoNewUser(){
        return "user_regist";
    }

    @PostMapping("/new")
    public String newUser(@Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidUserFormException("유효하지 않은 값입니다");
        }
        userService.join(userForm);
        return "redirect:/";
    }

}
