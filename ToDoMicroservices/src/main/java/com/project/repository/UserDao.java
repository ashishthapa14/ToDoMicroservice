package com.project.repository;

import com.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {

    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email=:email", nativeQuery = true)
    Optional<User> findUserByTheEmail(@Param("email") String email);

}
