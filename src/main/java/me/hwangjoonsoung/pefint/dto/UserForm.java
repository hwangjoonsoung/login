package me.hwangjoonsoung.pefint.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserForm {

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;
    @NotEmpty(message = "성함은 필수 입력값입니다.")
    private String name;
    @NotEmpty(message = "생년월일은 필수 입력값입니다.")
    private String birth;
    @NotEmpty(message = "성별은 필수 입력값입니다.")
    @Size(max = 1)
    private String sex;
    @NotEmpty(message = "휴대폰 번호는 필수 입력값입니다.")
    private String phone_number;
    private LocalDateTime date_join = LocalDateTime.now();
    private LocalDateTime date_update = LocalDateTime.now();
    private String enabled = "0";

}
