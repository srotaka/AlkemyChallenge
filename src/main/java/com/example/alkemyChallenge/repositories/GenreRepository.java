package com.example.alkemyChallenge.repositories;

import com.example.alkemyChallenge.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {

    @Modifying
    @Query("UPDATE Genre g SET g.status = true WHERE g.id = :id")
    void enableGenre(@Param("id") Integer id);
}
