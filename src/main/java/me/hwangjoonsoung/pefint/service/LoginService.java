package me.hwangjoonsoung.pefint.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.Token;
import me.hwangjoonsoung.pefint.repository.LoginRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public Long userAccess(Token token) {
        Long id = loginRepository.userAccess(token);
        return id;
    }
}
