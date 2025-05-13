package me.hwangjoonsoung.pefint.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
import me.hwangjoonsoung.pefint.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<User> getAllUser() {
        List<User> allUser = userRepository.findAll();
        return allUser;
    }

    public void join(UserForm userForm) {
        // user userFrom mapping
        User user = modelMapper.map(userForm, User.class);
        userRepository.joinNewUser(user);
    }

}
