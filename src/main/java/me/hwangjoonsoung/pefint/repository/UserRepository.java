package me.hwangjoonsoung.pefint.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public List<User> findAll() {
        List<User> users = em.createQuery("select u from User u ", User.class).getResultList();
        return users;
    }

    public void joinNewUser(User user) {
        em.persist(user);
    }

    public User findUserById(Long id) {
        User user = em.createQuery("select u from User u where u.id =:id ", User.class).setParameter("id", id).getSingleResult();
        return user;
    }

    public User findUserByEmail(String email) {
        User user = em.createQuery("select u from User u where u.email =:email ", User.class).setParameter("email", email).getSingleResult();
        return user;
    }
}
