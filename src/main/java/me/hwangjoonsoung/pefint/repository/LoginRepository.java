package me.hwangjoonsoung.pefint.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.Token;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    public Long userAccess(Token token){
        System.out.println(token);
        em.persist(token);
        Long id = token.getId();
        return id;
    }


}
