package me.hwangjoonsoung.pefint.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.hwangjoonsoung.pefint.domain.User;
import org.hibernate.engine.spi.EntityEntryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from User u")
    List<User> getAllUser();
}
