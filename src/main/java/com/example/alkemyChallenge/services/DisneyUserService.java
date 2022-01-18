package com.example.alkemyChallenge.services;

import com.example.alkemyChallenge.Enums.UserRol;
import com.example.alkemyChallenge.entities.DisneyUser;
import com.example.alkemyChallenge.repositories.DisneyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DisneyUserService implements UserDetailsService {

    @Autowired
    private DisneyUserRepository disneyUserRepository;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder enconder;

    private final String MESSAGE = "There is no user with email:  %s";

    @Transactional
    public DisneyUser createUser(DisneyUser disneyUser) throws Exception, IOException {

        disneyUser.setPassword(enconder.encode(disneyUser.getPassword()));

        if (disneyUserRepository.findAll().isEmpty()) {
            disneyUser.setUserRol(UserRol.ADMIN);
        } else {
            disneyUser.setUserRol(UserRol.USER);
        }

        disneyUser.setStatus(true);
        emailService.sendThread(disneyUser.getMail());
        return disneyUserRepository.save(disneyUser);

    }

    @Transactional
    public void update(Integer id, String name, String mail, String password, String image, MultipartFile photo) throws Exception, IOException {
        DisneyUser disneyUser = disneyUserRepository.findById(id).get();
        if (!photo.isEmpty()) {
            disneyUser.setPicture(pictureService.savePhoto(photo));
        }
        disneyUserRepository.save(disneyUser);
    }

    @Transactional(readOnly = true)
    public List<DisneyUser> getDisneyUser() {
        return disneyUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DisneyUser> getDisneyUsername(String mail) {
        return disneyUserRepository.findByMail(mail);
    }

    @Transactional(readOnly = true)
    public DisneyUser findById(Integer id) {
        return disneyUserRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public DisneyUser findByMail(String mail) {
        Optional<DisneyUser> optionalUser = disneyUserRepository.findByMail(mail);
        return optionalUser.orElse(null);
    }

    @Transactional
    public void enableUser(Integer id) {
        disneyUserRepository.enableUser(id);
    }

    @Transactional
    public void deleteUser(Integer id) {
        disneyUserRepository.deleteById(id);
    }

    @Override

    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        DisneyUser disneyUser = disneyUserRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MESSAGE, mail)));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + disneyUser.getUserRol().name());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("mail", disneyUser.getMail());
        session.setAttribute("password", disneyUser.getPassword());
        return new User(disneyUser.getMail(), disneyUser.getPassword(), Collections.singletonList(authority));

    }
}