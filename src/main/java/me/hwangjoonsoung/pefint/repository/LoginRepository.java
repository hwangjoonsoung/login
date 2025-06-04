package me.hwangjoonsoung.pefint.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.Token;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoginRepository {

    private final EntityManager em;

    public Long saveToken(Token token){
        em.persist(token);
        Long id = token.getId();
        return id;
    }

    public Token findToken(String token) {
        Token findToken = em.createQuery("select t from Token t where t.koken = :token",Token.class).setParameter("token", token).getSingleResult();
        return findToken;
    }



}
