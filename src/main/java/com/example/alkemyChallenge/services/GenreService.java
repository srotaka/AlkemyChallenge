package com.example.alkemyChallenge.services;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import com.example.alkemyChallenge.entities.Genre;
import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public void createGenre(String name, List<Movie> moviesList, String picture) {
        Genre genre = new Genre();

        genre.setName(name);
        genre.setMoviesList(moviesList);
        genre.setPicture(picture);
        genre.setStatus(true);
        genreRepository.save(genre);
    }

    @Transactional
    public void editGenre(Integer id, String name, List<Movie> moviesList, String picture) {
        Genre genre = genreRepository.findById(id).get();
        genreRepository.save(genre);
    }

    @Transactional
    public List<Genre> showAllGenres(){
        return genreRepository.findAll();
    }

    @Transactional
    public void deleteGenre(Integer id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    public void enableGenre(Integer id) {
        genreRepository.enableGenre(id);
    }

}
