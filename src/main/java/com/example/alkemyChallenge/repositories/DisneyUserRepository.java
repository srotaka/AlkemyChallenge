package com.example.alkemyChallenge.repositories;

import com.example.alkemyChallenge.entities.DisneyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DisneyUserRepository extends JpaRepository<DisneyUser, Integer> {

    Optional<DisneyUser> findByMail(String mail);

    @Modifying
    @Query("UPDATE DisneyUser u SET u.status = true WHERE u.id = :id")
    void enableUser(@Param("id") Integer id);

}