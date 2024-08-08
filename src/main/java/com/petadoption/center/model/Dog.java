package com.petadoption.center.model;

import com.petadoption.center.enums.DogBreeds;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "dogs")
public class Dog extends Pet {

    @Column(name = "primary_breed")
    private DogBreeds primaryBreed;

    @Column(name = "secondary_breed")
    private DogBreeds secondaryBreed;
}
