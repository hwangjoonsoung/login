package me.hwangjoonsoung.pefint.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String authority;
    private LocalDateTime date_create;
    private String enabled;

}
