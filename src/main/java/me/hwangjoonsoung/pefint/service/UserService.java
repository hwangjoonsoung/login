package me.hwangjoonsoung.pefint.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
import me.hwangjoonsoung.pefint.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public List<User> getAllUser() {
        List<User> allUser = userRepository.findAll();
        return allUser;
    }

    public void join(UserForm userForm) {
        User user = User.builder().email(userForm.getEmail()).birth(userForm.getBirth()).name(userForm.getName())
                .sex(userForm.getSex()).date_join(userForm.getDate_update()).date_update(null)
                .phone_number(userForm.getPhone_number()).enabled(userForm.getEnabled()).password(encoder.encode(userForm.getPassword()))
                .Role(userForm.getRole()).build();
        userRepository.joinNewUser(user);
    }

    public User findUserById(Long id) {
        User user = userRepository.findUserById(id);
        return user;
    }

    public void editUser(Long id,UserForm userForm) {
        userRepository.editUser(id,userForm);

    }
}
