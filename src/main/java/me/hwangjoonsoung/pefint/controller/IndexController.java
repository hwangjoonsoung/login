package me.hwangjoonsoung.pefint.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            System.out.println(auth.getName() +": "+auth.isAuthenticated() );
        }

        return "index";
    }

}
