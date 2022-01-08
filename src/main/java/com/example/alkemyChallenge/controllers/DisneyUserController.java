package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.DisneyUser;
import com.example.alkemyChallenge.services.DisneyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class DisneyUserController {

    @Autowired
    private DisneyUserService disneyUserService;

    private DisneyUser disneyUser;

    @PostMapping("/register")
    public DisneyUser createUser(@RequestBody DisneyUser disneyUser, MultipartFile photo) throws Exception {
        return disneyUserService.createUser(disneyUser, photo);
    }

    @PostMapping("/login")
    public DisneyUser loginUser(String name, String mail, String password, String picture, MultipartFile photo) {
        try {
            if (disneyUser != null) {
                disneyUserService.loadUserByUsername(disneyUser.getName());
                return disneyUser;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}