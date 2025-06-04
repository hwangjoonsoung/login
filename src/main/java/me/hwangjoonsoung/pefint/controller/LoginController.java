package me.hwangjoonsoung.pefint.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.configuration.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import me.hwangjoonsoung.pefint.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

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

    // todo: authorization에 access token 과 refresh token이 같이 넘어 오는 문제 해결

    @GetMapping("/token/check")
    public void loginSuccess(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            System.out.println(headerNames.nextElement());
        }
        String refreshToken = request.getHeader("authorization");
    }

    @GetMapping("/token/valid")
    public void tokenCheck(HttpServletRequest request) {
        String refreshToken = request.getHeader("authorization");
        if (refreshToken != null) {
            String tokenRemoveBearer = refreshToken.replace("Bearer ", "").trim();
            boolean isTokenValid = jwtProvider.validateToken(tokenRemoveBearer);
        }
    }

    @GetMapping("/token/email")
    public void getEmailFromToken(HttpServletRequest request) {
        String emailFromToken = "";
        String refreshToken = request.getHeader("authorization");
        if (refreshToken != null) {
            String tokenRemoveBearer = refreshToken.replace("Bearer ", "");
            emailFromToken = jwtProvider.getEmailFromToken(tokenRemoveBearer);
        }
    }

    @PostMapping("/token/logout")
    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader("authorization");
        if(accessToken != null){
            loginService.logoutUser(accessToken);
        }
    }
}
