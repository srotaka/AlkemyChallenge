package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.DisneyUser;
import com.example.alkemyChallenge.services.DisneyUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class DisneyUserController {

    @Autowired
    private DisneyUserService disneyUserService;

    private DisneyUser disneyUser;

    @PostMapping("/register")
    public DisneyUser createUser(@Valid @ModelAttribute DisneyUser disneyUser, BindingResult bindingResult, @RequestParam(value = "picture") MultipartFile photo) throws Exception {
        return disneyUserService.createUser(disneyUser, photo);
    }

    @PostMapping("/login")
    public DisneyUser loginUser(@Valid @ModelAttribute DisneyUser disneyUser, BindingResult bindingResult) {
        try {
            if (disneyUser != null) {
                UserDetails user = disneyUserService.loadUserByUsername(disneyUser.getMail());
                if (user != null) {
                    return disneyUserService.findByMail(disneyUser.getMail());
                } else {
                    return null;
                }

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/update")
    public void save(@Valid @ModelAttribute DisneyUser disneyUser, BindingResult result, @RequestParam(value = "picture") MultipartFile photo) throws Exception, IOException {
        if (!photo.isEmpty()) {
            disneyUser.setPicture(disneyUser.getPicture());
        }
        disneyUserService.createUser(disneyUser, photo);
    }

    @GetMapping("/all")
    public List<DisneyUser> getAll() {
        return disneyUserService.getDisneyUser();
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        try {
            disneyUserService.deleteUser(id);
            return "El usuario " + id + " fue eliminado";
        } catch (Exception e) {
            return "El usuario " + id + " no existe";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enableUser(@PathVariable("id") Integer id) {
        try {
            disneyUserService.enableUser(id);
            return "El usuario " + id + " fue habilitado";
        } catch (Exception e) {
            return "El usuario " + id + " no existe";
        }
    }
}