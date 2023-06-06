package com.example.dkrproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Location should not be null")
    private String name;

//    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<User> users;

    public Location (String name) {
        this.name = name;
    }

}
