package com.example.alkemyChallenge.services;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import com.example.alkemyChallenge.entities.Genre;
import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Movie createMovie(Movie movie) {
        movie.setStatus(true);
       return movieRepository.save(movie);
    }

    @Transactional
    public void editMovie(Integer id, String title, LocalDate releaseDate, Integer rate, List<DisneyCharacter> characterList, Genre genre, String picture) {

        Movie movie = movieRepository.findById(id).get();
        movieRepository.save(movie);
    }

    @Transactional
    public List<Movie> showAllMovies(){
     return movieRepository.findAll();
    }

    @Transactional
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

    @Transactional
    public void enableMovie(Integer id) {
        movieRepository.enableMovie(id);
    }

    @Transactional
    public List<Movie> findMovieByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    @Transactional
    public List<Movie> sortMovieByDate(String order){

        if (order.equals("ASC")){
            return movieRepository.findAll(Sort.by(Sort.Direction.ASC, "releaseDate"));
        } else if (order.equals("DESC")){
            return movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
        } else {
            return movieRepository.findAll();
        }
    }

    @Transactional
    public List<Movie> moviesOrderByDateDesc(){
        return movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
    }

    @Transactional
    public List<Object[]> showMoviesByTitlePictureDate(){
        return movieRepository.showMoviesByTitlePictureDate();
    }
    @Transactional
    public List<Movie> findMovieByGenre(String genre){
        return movieRepository.findByGenre(genre);
    }

    @Transactional
    public Optional<Movie> findById(Integer id){
        return movieRepository.findById(id);
    }
}
