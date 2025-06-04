package me.hwangjoonsoung.pefint.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.configuration.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import me.hwangjoonsoung.pefint.service.LoginService;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest , HttpServletResponse httpServletResponse) {
        TokenResponse tokenResponse = loginService.loginUser(loginRequest);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenResponse.getRefreshToken()).httpOnly(true).secure(true)
                .path("/")
                .sameSite("none")
                .maxAge(60 * 60 * 24)
                .build();
        httpServletResponse.setHeader("Set-Cookie" ,cookie.toString());
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
    public void loginSuccess(HttpServletRequest request , @CookieValue(value = "refreshToken" , required = false) String refreshToken) {
        String accessToken = request.getHeader("authorization");
        System.out.println("accessToken = " + accessToken);
        if(refreshToken != null ){
            boolean isTokenValidate = jwtProvider.validateToken(refreshToken);
            System.out.println("isTokenValidate = " + isTokenValidate);
        }
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
