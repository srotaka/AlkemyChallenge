package com.example.alkemyChallenge.entities;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;


@Entity
@Table(name = "genres")
@SQLDelete(sql = "UPDATE genres SET status = false WHERE id = ?")
@Getter
@Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank(message = "Name is required")
    private String name;
    
    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Movie> moviesList;

    @Column
    private String picture;

    @Column
    private Boolean status;

    //Constructors

    public Genre() {
    }

    public Genre(Integer id, String name, List<Movie> moviesList, String picture, Boolean status) {
        this.id = id;
        this.name = name;
        this.moviesList = moviesList;
        this.picture = picture;
        this.status = status;
    }

    @Override
    public String toString() {
        return  "\t~ GENRES ~" +
                "\n • ID: " + id +
                "\n • Genre: " + name +
                "\n • Films: " + moviesList +
                "\n • Picture: " + picture  +
                "\n • Status: " + status;
    }
}
