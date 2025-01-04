package com.example.focus.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProfileEditor {
    @Id
    private Integer id;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @PositiveOrZero(message = "Number of posts cannot be negative")
    private Integer numberOfPosts;

    @Pattern(regexp = "^(https?|ftp)://[^\s/$.?#].[^\s]*$", message = "Image URL must be valid (http, https, or ftp)")
    private String image;  // URL of the image

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id")
    private MyUser myUser;

    @OneToMany
    private Set<Media> medias;


}