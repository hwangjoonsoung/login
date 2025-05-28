package me.hwangjoonsoung.pefint.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.configuration.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @GetMapping("/login")
    public String userLogin() {
        return "/user/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtProvider.generateToken(request.getEmail());
        TokenResponse tokenResponse = new TokenResponse(token);
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

    @GetMapping("/success")
    public void loginSuccess(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
    }
}
