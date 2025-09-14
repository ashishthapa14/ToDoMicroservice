package com.project.repository;

import java.util.List;

import com.project.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ToDoDao extends JpaRepository<ToDo, Integer> {
    @Query(value = "SELECT * FROM todos t where t.fk_user= :email",nativeQuery = true)
    List<ToDo> findByFkUser(@Param(value = "email") String email);
}
