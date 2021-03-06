package com.example.alkemyChallenge.repositories;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisneyCharacterRepository extends JpaRepository<DisneyCharacter, Integer> {

    @Modifying
    @Query("UPDATE DisneyCharacter c SET c.status = true WHERE c.id = :id")
    void enableCharacter(@Param("id") Integer id);

    @Query(value = "SELECT picture, name FROM characters", nativeQuery = true)
    List<Object[]> showNamePictureCharacter();

    @Query(value = "SELECT name, picture FROM disney-character", nativeQuery = true)
    List<DisneyCharacter> showCharacterByNamePicture();

    @Query(value = "SELECT c.name, m.title FROM characters c INNER JOIN rel_character_movie r ON c.id = r.id_character INNER JOIN movie m ON  m.movie_id = r.id_movie WHERE m.movie_id= ?1", nativeQuery = true)
    List<DisneyCharacter> findCharacterByMovieID(Integer movieId);

    List<DisneyCharacter> findByName(String name);

    List<DisneyCharacter> findByAge(Integer age);

}
