package me.hwangjoonsoung.pefint.service;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUser(){
        List<User> allUser = userRepository.getAllUser();
        return allUser;
    }

}
