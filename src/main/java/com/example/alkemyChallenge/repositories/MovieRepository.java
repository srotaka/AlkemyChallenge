package com.example.alkemyChallenge.repositories;

import com.example.alkemyChallenge.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Modifying
    @Query("UPDATE Movie m SET m.status = true WHERE m.id = :id")
    void enableMovie(@Param("id") Integer id);

    // Query para mostrar movies con 3 atributos
    @Query(value = "SELECT title, picture, releaseDate FROM movie", nativeQuery = true)
    List<Object[]> showMoviesByTitlePictureDate();

    List<Movie> findByTitle(String title);

    List<Movie> findByGenre(String genre);

}
