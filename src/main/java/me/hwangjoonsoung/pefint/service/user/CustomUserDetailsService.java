package me.hwangjoonsoung.pefint.service.user;

import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

//    일반모드
//    @Override
//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        User user = userRepository.findUserByEmail(userEmail);
//        return org.springframework.security.core.userdetails.User.withUsername(user.getName()).password(user.getPassword()).roles("USER").build();
//    }

//    custom mode security6
//    @Override
//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        User user = userRepository.findUserByEmail(userEmail);
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getId().toString()).password(user.getPassword()).roles(user.getRole()).build();
//        return userDetails;
//    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(userEmail);
        org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
        return userDetail;
    }

    public UserDetails loadUserByUsername(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findUserById(userId);
        org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of());
        return userDetail;
    }
}
