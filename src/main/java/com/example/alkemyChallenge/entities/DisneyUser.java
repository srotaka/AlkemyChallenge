package com.example.alkemyChallenge.entities;


import com.example.alkemyChallenge.Enums.UserRol;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "disney_users")
@SQLDelete(sql = "UPDATE disney_users SET status = false WHERE id = ?")
@Getter
@Setter
public class DisneyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    @Column
    private String name;


    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Mail format must be valid")
    @Column(unique = true)
    private String mail;


    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRol userRol;

    private String picture;

    private Boolean status;

    private String token;

      public DisneyUser() {
    }

    public DisneyUser(Integer id, String name, String mail, String password, UserRol userRol, String picture, Boolean status, String token) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userRol = userRol;
        this.picture = picture;
        this.status = status;
        this.token = token;
    }
}
