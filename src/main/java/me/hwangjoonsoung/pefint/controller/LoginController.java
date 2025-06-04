package me.hwangjoonsoung.pefint.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.configuration.jwt.JwtProvider;
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
    private final JwtProvider jwtProvider;

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
        }
        return "/user/LoginSuccess";
    }*/

    @GetMapping("/token/check")
    public void loginSuccess(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
    }

    @GetMapping("/token/valid")
    public void tokenCheck(HttpServletRequest request){
        String authorizationToken = request.getHeader("Authorization");
        if(authorizationToken != null){
            String tokenRemoveBearer = authorizationToken.replace("Bearer ", "").trim();
            boolean isTokenValid = jwtProvider.validateToken(tokenRemoveBearer);
        }
    }

    @GetMapping("/token/email")
    public void getEmailFromToken(HttpServletRequest request){
        String emailFromToken= "";
        String authorizationToken = request.getHeader("Authorization");
        if(authorizationToken !=null){
            String tokenRemoveBearer = authorizationToken.replace("Bearer ", "");
            emailFromToken = jwtProvider.getEmailFromToken(tokenRemoveBearer);
        }

    }
}
