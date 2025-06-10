package me.hwangjoonsoung.pefint.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.hwangjoonsoung.pefint.domain.User;

@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserJsonUsingRedis {

    private String name;
    private String role;

    public static UserJsonUsingRedis userJsonUsingRedis(User user) {
        return new UserJsonUsingRedis(user.getName(), user.getRole());
    }
}
