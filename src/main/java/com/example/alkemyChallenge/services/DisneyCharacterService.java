package com.example.alkemyChallenge.services;

import com.example.alkemyChallenge.entities.DisneyCharacter;
import com.example.alkemyChallenge.entities.Movie;
import com.example.alkemyChallenge.repositories.DisneyCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DisneyCharacterService {

    @Autowired
    private DisneyCharacterRepository disneyCharacterRepository;
    @Autowired
    private PictureService pictureService;

    @Transactional
    public DisneyCharacter createCharacter(DisneyCharacter disneyCharacter) {

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
    public List<Object[]> showNamePictureCharacter(){
        return disneyCharacterRepository.showNamePictureCharacter();
    }

    @Transactional
    public List<DisneyCharacter> showAllCharacter(){
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
    public List<Object []> findCharacterByMovieID(Integer id){
        List<Object[]> characterList = disneyCharacterRepository.findCharacterByMovieID(id);

        return disneyCharacterRepository.findCharacterByMovieID(id);
    }

    @Transactional
    public Optional<DisneyCharacter> findCharacterById(Integer id){
        return disneyCharacterRepository.findById(id);
    }



}
