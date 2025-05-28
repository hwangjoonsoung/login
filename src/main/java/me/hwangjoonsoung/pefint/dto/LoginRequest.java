package me.hwangjoonsoung.pefint.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class LoginRequest {

    private String email;
    private String password;

}
