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

    @NotBlank(message = "Mail is required")
    @Email(message = "Mail format is  ")
    @Column(unique = true)
    private String mail;

    @NotBlank(message = "Password is required")
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRol userRol;

    private String picture;

    private Boolean status;

      public DisneyUser() {
    }

    public DisneyUser(Integer id, String name, String mail, String password, UserRol userRol, String picture, Boolean status) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userRol = userRol;
        this.picture = picture;
        this.status = status;
    }
}
