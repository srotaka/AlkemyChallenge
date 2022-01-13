package com.example.alkemyChallenge.entities;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;


@Entity
@Table(name = "characters")
@SQLDelete(sql = "UPDATE characters SET status = false WHERE id = ?")
@Getter
@Setter
public class DisneyCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Age is required")
    @Positive(message = "Age cannot be lower than 0")
    private Integer age;

    @Column(nullable = false)
    @NotBlank(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0.1")
    private Double weight;

    @Column(nullable = false)
    @NotBlank(message = "A short story is required")
    private String story;

    @NotEmpty(message = "Filmography is required")
    @ManyToMany(mappedBy = "charactersList")
    @JsonIgnore
    private List<Movie> filmography;

    @Column
    private String picture;

    @Column
    private Boolean status;


    //Constructors
    public DisneyCharacter(Integer id, String name, Integer age, Double weight, String story, List<Movie> filmography, String picture, Boolean status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.story = story;
        this.filmography = filmography;
        this.picture = picture;
        this.status = status;

    }

    public DisneyCharacter() {
    }

    @Override
    public String toString() {
        return "\t ~ Character:\n" +
                "• ID:" + id +
                "\n• Name: " + name +
                "\n• Age: " + age +
                "\n• Weight: " + weight +
                "Kg.\n• Story:" + story +
                "\n• Filmography: " + filmography +
                "\n• Picture: " + picture +
                "\n• Status:" + status;
    }
}
