package me.hwangjoonsoung.pefint.service.login;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.service.RedisService;
import me.hwangjoonsoung.pefint.util.jwt.JwtProvider;
import me.hwangjoonsoung.pefint.domain.Token;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.LoginRequest;
import me.hwangjoonsoung.pefint.dto.TokenResponse;
import me.hwangjoonsoung.pefint.repository.login.LoginRepository;
import me.hwangjoonsoung.pefint.repository.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public TokenResponse loginUser(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String accessToken = "";
        String refreshToken = jwtProvider.generateRefreshToken(request.getEmail());
        if(refreshToken != null){
            User user = userRepository.findUserByEmail(request.getEmail());
            accessToken = UUID.randomUUID().toString();
            Token token = Token.builder().user(user).token(accessToken).build();
            loginRepository.saveToken(token);


            redisService.save(redisService.createRedisKey(user.getId()), );
        }
        TokenResponse tokenResponse = TokenResponse.builder().refreshToken(refreshToken).accessToken(accessToken).build();
        return tokenResponse;
    }

    public void logoutUser(String token){
        loginRepository.expiredToken(token);
    }


}
