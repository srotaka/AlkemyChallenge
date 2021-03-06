package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import com.example.alkemyChallenge.services.DisneyCharacterService;
import com.example.alkemyChallenge.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public DisneyCharacter saveCharacter(@Valid @ModelAttribute DisneyCharacter disneyCharacter, BindingResult result, @RequestParam (value = "picture") MultipartFile photo) throws Exception{

        return disneyCharacterService.createCharacter(disneyCharacter, photo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "delete/{id}")
    public String deleteCharacter(@PathVariable("id") Integer id){
        try {
            disneyCharacterService.deleteCharacter(id);
            return "Character N?? "+id+ " has been removed.";
        } catch (Exception e) {
            return "Character N?? "+id+" does not exist.";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "enable/{id}")
    public String enableCharacter(@PathVariable("id") Integer id) {
        try {
            disneyCharacterService.enableCharacter(id);
            return "Character N?? " + id + " was enabled.";
        } catch (Exception e) {
            return "Character N??  " + id + " does not exist.";
        }
    }

}
