package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.DisneyUser;
import com.example.alkemyChallenge.services.DisneyUserService;
import com.example.alkemyChallenge.services.PictureService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class    DisneyUserController {

    @Autowired
    private DisneyUserService disneyUserService;

    @Autowired
    private PictureService pictureService;

    private DisneyUser disneyUser;

/*    @PostMapping("/register")
    public DisneyUser createUser(@Valid @ModelAttribute DisneyUser disneyUser, BindingResult bindingResult, @RequestParam(value = "picture") MultipartFile photo) throws Exception {
        return disneyUserService.createUser(disneyUser, photo);
    }*/

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute DisneyUser disneyUser, BindingResult result, @RequestParam(value = "picture") MultipartFile photo) {
        try {
            List<DisneyUser> disneyUsersList = disneyUserService.getDisneyUser();
            for (DisneyUser disneyUser1 : disneyUsersList) {
                if (disneyUser1.getMail().equals(disneyUser.getMail())) {
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Email already register.");
                }
            }
            if (!photo.isEmpty()) {
                disneyUser.setPicture(pictureService.savePhoto(photo));
            }

            DisneyUser usuario = disneyUserService.createUser(disneyUser);
            String token = getJWTToken(disneyUser.getMail());
            usuario.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(disneyUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error. " + e.getMessage() + ".\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @ModelAttribute DisneyUser disneyUser, BindingResult result) throws Exception {
        try {
            if (disneyUser != null) {
                UserDetails userDetails = disneyUserService.loadUserByUsername(disneyUser.getMail());
                if (userDetails == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error: User not found.\"}");
                }
                String token = getJWTToken(disneyUser.getMail());
                disneyUser.setToken(token);
                return ResponseEntity.status(HttpStatus.OK).body("Mail: " + disneyUser.getMail() + "\nToken: " + disneyUser.getToken());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("alkemyJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2700000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    @GetMapping("/all")
    public List<DisneyUser> getAll() {
        return disneyUserService.getDisneyUser();
    }

}