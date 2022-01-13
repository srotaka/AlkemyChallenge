package com.example.alkemyChallenge.controllers;

import com.example.alkemyChallenge.entities.DisneyUser;
import com.example.alkemyChallenge.services.DisneyUserService;
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
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Ya se encuentra registrado un usuario con ese nombre");
                }
            }
            DisneyUser usuario = disneyUserService.createUser(disneyUser, photo);
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
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error. No se encontró un usuario registrado con ese nombre y contraseña .\"}");
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
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    @GetMapping("/all")
    public List<DisneyUser> getAll() {
        return disneyUserService.getDisneyUser();
    }
/*
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
    }*/
}