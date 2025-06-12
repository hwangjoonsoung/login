package me.hwangjoonsoung.pefint.repository.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
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

    public void editUser(Long id , UserForm userForm) {
        User user = em.createQuery("select u from User u where u.id = :id", User.class).setParameter("id", id).getSingleResult();
        user.setBirth(userForm.getBirth());
        user.setEmail(userForm.getEmail());
        user.setName(userForm.getName());
        user.setPhone_number(userForm.getPhone_number());
    }
}
