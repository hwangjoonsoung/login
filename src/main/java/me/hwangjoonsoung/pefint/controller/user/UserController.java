package me.hwangjoonsoung.pefint.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.customException.InvalidUserFormException;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
import me.hwangjoonsoung.pefint.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/alluser")
    public String getAllUser(Model model){
        List<User> allUser = userService.getAllUser();
        model.addAttribute("users", allUser);
        return "/user/users";
    }

    @GetMapping("/new")
    public String newUser(){
        return "/user/new";
    }

    @PostMapping("/new")
    public String newUser(@Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new InvalidUserFormException("유효하지 않은 값입니다");
        }
        userService.join(userForm);
        return "redirect:/user/new";
    }

    @GetMapping("/i/{id}")
    public String findUserById(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "/user/info";
    }

    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null){
            String userId = authentication.getName();
            User user = userService.findUserById(Long.parseLong(userId));
            model.addAttribute("user", user);
        }

        return "/user/profile";
    }

    @PostMapping("/profile")
    public String userProfile(UserForm userForm) {
        Long id = getAuthId();
        userService.editUser(id,userForm);
        return "redirect:/user/success";
    }

    private static Long getAuthId() {
        long id = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            id = Long.parseLong(authentication.getName());
        }
        return id;
    }

}
