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
    @Query("UPDATE Movie m SET m.status = true WHERE m.movieId = :movieId")
    void enableMovie(@Param("movieId") Integer movieId);

    @Query("SELECT title, picture, releaseDate FROM Movie")
    List<Object[]> showMoviesByTitlePictureDate();

    List<Movie> findByTitle(String title);

    @Query(value = "SELECT * FROM movie WHERE genre_id = ?1", nativeQuery = true)
    List<Movie> findByGenre(Integer genre);

}
