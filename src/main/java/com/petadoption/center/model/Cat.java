package com.petadoption.center.model;

import com.petadoption.center.enums.CatBreeds;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cats")
public class Cat extends Pet {

    @Column(name = "primary_breed")
    private CatBreeds primaryBreed;

    @Column(name = "secondary_breed")
    private CatBreeds secondaryBreed;
}
