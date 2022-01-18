package com.example.alkemyChallenge.services;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.repositories.DisneyCharacterRepository;
import com.example.alkemyChallenge.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DisneyCharacterService {

    @Autowired
    private DisneyCharacterRepository disneyCharacterRepository;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public DisneyCharacter createCharacter(DisneyCharacter disneyCharacter, MultipartFile photo) throws Exception {

        if (!photo.isEmpty()) disneyCharacter.setPicture(pictureService.savePhoto(photo));
        disneyCharacter.setStatus(true);
        return disneyCharacterRepository.save(disneyCharacter);
    }

    @Transactional
    public void editDisneyCharacter(Integer id, String name, Integer age, Double weight, String story, List<Movie> filmography, String picture) {
        DisneyCharacter disneyCharacter = disneyCharacterRepository.findById(id).get();
        disneyCharacterRepository.save(disneyCharacter);
    }

    @Transactional
    public void deleteCharacter(Integer id) {
        disneyCharacterRepository.deleteById(id);
    }

    @Transactional
    public void enableCharacter(Integer id) {
        disneyCharacterRepository.enableCharacter(id);
    }

    @Transactional
    public List<Object[]> showNamePictureCharacter() {
        return disneyCharacterRepository.showNamePictureCharacter();
    }

    @Transactional
    public List<DisneyCharacter> showAllCharacter() {
        return disneyCharacterRepository.findAll();
    }

    @Transactional
    public List<DisneyCharacter> findCharacterByName(String name) {
        return disneyCharacterRepository.findByName(name);
    }

    @Transactional
    public List<DisneyCharacter> findCharacterByAge(Integer age) {
        return disneyCharacterRepository.findByAge(age);
    }

    @Transactional
    public List<DisneyCharacter> findCharacterByMovieID(Integer movieId) {
        return disneyCharacterRepository.findCharacterByMovieID(movieId);

    }

    @Transactional
    public Optional<DisneyCharacter> findCharacterById(Integer id) {
        return disneyCharacterRepository.findById(id);
    }

    @Transactional
    public List<DisneyCharacter> findCharactersByMovie(Integer movieId) throws Exception {
        try {
            Movie movie = movieRepository.findById(movieId).get();
            if (movie == null){
                throw new Exception("No movie found with id " + movieId);
            }
            List<DisneyCharacter> charactersListByMovie = movie.getCharactersList();
            if (!charactersListByMovie.isEmpty()) {
                return charactersListByMovie;
            } else {
                throw new Exception("This movie has no characters yet");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
