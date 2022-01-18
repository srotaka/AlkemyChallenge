package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.Genre;
import com.example.alkemyChallenge.services.GenreService;
import com.example.alkemyChallenge.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;
    @Autowired
    private PictureService pictureService;

    @GetMapping()
    public List<Genre> showAllGenres(){
        return genreService.showAllGenres();
    }

    @PostMapping("/save")
    public Genre saveGenre(@Valid @ModelAttribute Genre genre, BindingResult bindingResult, @RequestParam(value="picture")MultipartFile photo) throws Exception{
        if (!photo.isEmpty()) {
            genre.setPicture(pictureService.savePhoto(photo));
        }
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

    @GetMapping("/{id}")
    public Optional<Genre> getById(@PathVariable("id") Integer id) {
        return genreService.findById(id);
    }

}
