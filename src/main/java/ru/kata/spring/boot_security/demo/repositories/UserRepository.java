package ru.kata.spring.boot_security.demo.repositories;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "user-entity-graph")
    @Query("select u from User u where u.login = :userName")
    User findByUsername(@Param("userName") String userName);
}
