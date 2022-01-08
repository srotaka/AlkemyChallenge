package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.Genre;
import com.example.alkemyChallenge.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping()
    public List<Genre> showAllGenres(){
        return genreService.showAllGenres();
    }

    @PostMapping("/save")
    public Genre saveGenre(@RequestBody Genre genre){
        return genreService.createGenre(genre);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteGenre(@PathVariable("id") Integer id){
        try {
            genreService.deleteGenre(id);
            return "Genre Nº "+id+ " has been removed.";
        } catch (Exception e) {
            return "Genre Nº "+id+" does not exist.";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enableGenre(@PathVariable("id") Integer id) {
        try {
            genreService.enableGenre(id);
            return "Genre Nº " + id + " was enabled.";
        } catch (Exception e) {
            return "Genre Nº  " + id + " does not exist.";
        }
    }





}
