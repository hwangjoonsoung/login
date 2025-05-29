package me.hwangjoonsoung.pefint.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;
    private String koken;
    @Column(length = 1)
    private char is_refresh_token;
    private LocalDateTime date_expired ;
    private LocalDateTime date_create ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void settingDefault(){
        this.date_create = LocalDateTime.now();
        this.date_expired = LocalDateTime.now();
    }
}
