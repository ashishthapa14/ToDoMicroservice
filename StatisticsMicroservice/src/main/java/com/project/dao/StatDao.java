package com.project.dao;

import com.project.model.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatDao extends JpaRepository<Stat,Integer> {

    @Query(value = "select * from statistic where email=:email order by date desc limit 10",nativeQuery = true)
    List<Stat> getLastStatistics(@Param("email") String email);
}
