package me.hwangjoonsoung.pefint.controller;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/alluser")
    public String getAllUser(Model model){
        List<User> allUser = userService.getAllUser();
        System.out.println(allUser);
        model.addAttribute("users", allUser);
        return "allusers";
    }

}
