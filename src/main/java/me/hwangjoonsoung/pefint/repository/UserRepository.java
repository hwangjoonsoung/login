package me.hwangjoonsoung.pefint.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import me.hwangjoonsoung.pefint.dto.UserForm;
import org.hibernate.engine.spi.EntityEntryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public List<User> findAll(){
        List<User> users = em.createQuery("select u from User u ", User.class).getResultList();
        return users;
    }

   public void joinNewUser(User user){
        em.persist(user);
   }

}
