package com.example.alkemyChallenge.controllers;


import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.services.MovieService;
import com.example.alkemyChallenge.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private PictureService pictureService;

    @GetMapping
    public List<Object[]> showMoviesByTitlePictureDate(){
        return movieService.showMoviesByTitlePictureDate();
    }

    @GetMapping("/all")
    public List<Movie> getAll() {
        return movieService.showAllMovies();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getById(@PathVariable("id") Integer id) {
        return movieService.findById(id);
    }

    @GetMapping(params = "title")
    public List<Movie> getByTitle(@RequestParam("title") String title) {
        return movieService.findMovieByTitle(title);
    }

    @GetMapping(value = "", params = "genre")
    public List<Movie> getByGenre(@RequestParam("genre") Integer genre) {
        return movieService.findMovieByGenre(genre);
    }


    @GetMapping(params = "order")
    public List<Movie> getByOrder(@RequestParam("order") String order){
        return movieService.sortMovieByDate(order);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteMovie(@PathVariable("id") Integer id){
        try {
            movieService.deleteMovie(id);
            return "Movie Nº "+id+ " has been removed.";
        } catch (Exception e) {
            return "Movie Nº "+id+" does not exist.";
        }
    }
    @GetMapping(path = "enable/{id}")
    public String enableMovie(@PathVariable("id") Integer id) {
        try {
            movieService.enableMovie(id);
            return "Movie Nº " + id + " was enabled.";
        } catch (Exception e) {
            return "Movie Nº  " + id + " does not exist.";
        }
    }

    @PostMapping("/save")
    public Movie saveMovie(@Valid @ModelAttribute Movie movie, BindingResult result, @RequestParam (value = "picture") MultipartFile photo) throws Exception{
        if (!photo.isEmpty()) {
            movie.setPicture(pictureService.savePhoto(photo));
        }
        return movieService.createMovie(movie);
    }
}
