package com.example.alkemyChallenge.services;

import com.example.alkemyChallenge.entities.Genre;
import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public Genre createGenre(Genre genre) {
        genre.setStatus(true);
        return genreRepository.save(genre);
    }

    @Transactional
    public void editGenre(Integer id, String name, List<Movie> moviesList, String picture) {
        Genre genre = genreRepository.findById(id).get();
        genreRepository.save(genre);
    }

    @Transactional
    public List<Genre> showAllGenres() {
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

    @Transactional
    public Optional<Genre> findById(Integer id) {
        return genreRepository.findById(id);
    }
}
