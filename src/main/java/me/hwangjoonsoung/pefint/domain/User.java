package me.hwangjoonsoung.pefint.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    @Column(length = 6)
    private String birth;

    @Column(length = 1)
    private String sex;
    private String phone_number;
    private LocalDateTime date_join;
    private LocalDateTime date_update;

    @Column(length = 1)
    private String enabled;

}
