package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.services.DisneyCharacterService;
import com.example.alkemyChallenge.services.PictureService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class DisneyCharacterController {

    @Autowired
    private DisneyCharacterService disneyCharacterService;
    @Autowired
    private PictureService pictureService;

    @GetMapping
    public List<Object[]> showNamePictureCharacter(){
        return disneyCharacterService.showNamePictureCharacter();
    }

    @GetMapping("/all")
    public List<DisneyCharacter> showAllCharacter() {
        return disneyCharacterService.showAllCharacter();
    }

    @GetMapping(path = "/{id}")
    public Optional<DisneyCharacter> findById(@PathVariable("id") Integer id) {
        return disneyCharacterService.findCharacterById(id);
    }

    @GetMapping(params = "name")
    public List<DisneyCharacter> findByName(@RequestParam("name") String name) {
        return disneyCharacterService.findCharacterByName(name);
    }

    @GetMapping(params = "age")
    public List<DisneyCharacter> findByAge(@RequestParam("age") Integer age) {
        return disneyCharacterService.findCharacterByAge(age);
    }


    @RequestMapping(value ="", params = "movies", method = RequestMethod.GET)
    public ResponseEntity<?> charactersByMovieList(@RequestParam("movies") Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(disneyCharacterService.findCharactersByMovie(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error: " + e.getMessage() + ".\"}");
        }
    }

    @PostMapping("/save")
    public DisneyCharacter saveCharacter(@Valid @ModelAttribute DisneyCharacter disneyCharacter, BindingResult result, @RequestParam (value = "picture") MultipartFile photo) throws Exception{

        return disneyCharacterService.createCharacter(disneyCharacter, photo);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteCharacter(@PathVariable("id") Integer id){
        try {
            disneyCharacterService.deleteCharacter(id);
            return "Character Nº "+id+ " has been removed.";
        } catch (Exception e) {
            return "Character Nº "+id+" does not exist.";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enableCharacter(@PathVariable("id") Integer id) {
        try {
            disneyCharacterService.enableCharacter(id);
            return "Character Nº " + id + " was enabled.";
        } catch (Exception e) {
            return "Character Nº  " + id + " does not exist.";
        }
    }


}
