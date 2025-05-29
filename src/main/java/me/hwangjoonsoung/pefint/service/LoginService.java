package me.hwangjoonsoung.pefint.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.configuration.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.domain.Token;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import me.hwangjoonsoung.pefint.repository.LoginRepository;
import me.hwangjoonsoung.pefint.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public TokenResponse loginUser(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtProvider.generateToken(request.getEmail());
        if(token != null){
            User user = userRepository.findUserByEmail(request.getEmail());
            Token accessToken = Token.builder().user(user).koken(token).build();
            loginRepository.saveToken(accessToken);
        }
        TokenResponse tokenResponse = new TokenResponse(token);
        return tokenResponse;
    }
}
