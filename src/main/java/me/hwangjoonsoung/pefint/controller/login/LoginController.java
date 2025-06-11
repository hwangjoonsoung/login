package me.hwangjoonsoung.pefint.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.util.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import me.hwangjoonsoung.pefint.service.login.LoginService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> loginSuccess(HttpServletRequest request , @CookieValue(value = "refreshToken" , required = false) String refreshToken) {
        if(refreshToken != null ){
            boolean isTokenValidate = jwtProvider.validateToken(refreshToken);
            if(isTokenValidate){
                String subject = jwtProvider.getSubjectFromToken(refreshToken);
            }
            return ResponseEntity.ok(isTokenValidate);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
    }

    @GetMapping("/token/email")
    public void getEmailFromToken(HttpServletRequest request , @CookieValue(value = "refreshToken") String refreshToken) {
        String emailFromToken = "";
        if (refreshToken != null) {
            emailFromToken = jwtProvider.getSubjectFromToken(refreshToken);
        }
    }

    @PostMapping("/token/logout")
    public ResponseEntity<?> logout(HttpServletRequest request ,@CookieValue(value = "refreshToken") String refreshToken ) {
        String accessToken = request.getHeader("authorization");
        if(accessToken != null){
            accessToken = accessToken.substring(7);
            loginService.logoutUser(accessToken);
        }
        ResponseCookie expiredCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("none")
                .maxAge(0)
                .build();

        return ResponseEntity.ok().header("Set-Cookie" , expiredCookie.toString()).body("logout success");
    }
}
