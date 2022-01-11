package com.example.alkemyChallenge.entities;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;


@Entity
@SQLDelete(sql = "UPDATE movie SET status = false WHERE id = ?")
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    //@NotBlank(message = "Release date is required")
    @Column(nullable = false)
    private LocalDate releaseDate;

    @NotBlank(message = "Rate is required")
    @Column
    @Min(value = 1, message = "Rate must be at least 1")
    @Max(value = 5, message = "Rate cannot be greater than 5")
    private Integer rate;

    @ManyToMany
    @JoinTable(name = "rel_character_movie",
            joinColumns = @JoinColumn(name = "id_movie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_character", nullable = false))
    //@NotEmpty(message = "Character List is required")
    private List<DisneyCharacter> charactersList;

    @ManyToOne
    private Genre genre;

    @Column
    private String picture;

    @Column
    private Boolean status;


    //Constructors

    public Movie() {
    }

    public Movie(Integer id, String title, LocalDate releaseDate, Integer rate, List<DisneyCharacter> charactersList, Genre genre, String picture, Boolean status) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rate = rate;
        this.charactersList = charactersList;
        this.genre = genre;
        this.picture = picture;
        this.status = status;
    }

    @Override
    public String toString() {
        return "MovieSerie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", rate=" + rate +
                ", charactersList:" + charactersList +
                ", genre=" + genre +
                ", picture='" + picture + '\'' +
                ", status=" + status +
                '}';
    }
}
