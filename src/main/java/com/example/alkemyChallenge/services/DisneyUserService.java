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
    public DisneyUser createUser(DisneyUser disneyUser, MultipartFile photo) throws Exception, IOException {


        // disneyUser.setName(name);
        // disneyUser.setMail(mail);
      //  disneyUser.setPassword(enconder.encode(disneyUser.getPassword()));
        // disneyUser.setPassword(password);

        if (disneyUserRepository.findAll().isEmpty()) {
            disneyUser.setUserRol(UserRol.ADMIN);
        } else {
            disneyUser.setUserRol(UserRol.USER);
        }

        // if (!photo.isEmpty()) disneyUser.setPicture(pictureService.savePhoto(photo));

        disneyUser.setStatus(true);

        return disneyUserRepository.save(disneyUser);
        //emailService.sendThread(mail);
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
    //Este método entra en juego cuando el usuario se loguea
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        //chequea que el correo exista: permite el acceso o lanza una excepción
        DisneyUser disneyUser = disneyUserRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MESSAGE, mail)));
        //La palabra ROLE_ (es la forma que reconoce los roles Spring) concatenada con el rol y el nombre de ese rol
        //Acá genera los permisos y se los pasa al User
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + disneyUser.getUserRol().name());

        //El Servlet se castea
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        //Si la sesión no está creada, con true la va a crear
        HttpSession session = attributes.getRequest().getSession(true);

        //session.setAttribute("id", user.getId());
        session.setAttribute("name", disneyUser.getName());
        session.setAttribute("mail", disneyUser.getMail());
        //session.setAttribute("password", user.getPassword());
        session.setAttribute("userRol", disneyUser.getUserRol().name());
        session.setAttribute("image", disneyUser.getPicture());
        //session.setAttribute("status", user.getStatus());

        //le paso las autorizaciones en el collections
        return new User(disneyUser.getMail(), disneyUser.getPassword(), Collections.singletonList(authority));


    }
}