package me.hwangjoonsoung.pefint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hwangjoonsoung.pefint.domain.User;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Component
@Getter
public class UserJsonUsingRedis {

    private String name;
    private String role;
    private String token;

    public static UserJsonUsingRedis userJsonUsingRedis(User user , String token) {
        return new UserJsonUsingRedis(user.getName(), user.getRole(),token);
    }
}
