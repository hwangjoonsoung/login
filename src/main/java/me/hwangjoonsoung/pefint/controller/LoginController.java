package me.hwangjoonsoung.pefint.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import me.hwangjoonsoung.pefint.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String userLogin() {
        return "/user/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest request) {
        TokenResponse tokenResponse = loginService.loginUser(request);
        return ResponseEntity.ok(tokenResponse);
    }

/*  spring security6
    @GetMapping("/success")
    public String loginSuccess(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println(auth.getName() + ": " + auth.isAuthenticated());
        }
        return "/user/LoginSuccess";
    }*/

    @GetMapping("/token/check")
    public void loginSuccess(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
    }

    @GetMapping("/token/valid")
    public void tokenCheck(HttpServletRequest request){
        String authorizationToken = request.getHeader("Authorization");
        if(authorizationToken != null){
        }

        //token이 유효한지 확인
        //유효하다면 리프레쉬 토큰 발급 안하고 유요하지 않다면 발급
        //
    }
}
