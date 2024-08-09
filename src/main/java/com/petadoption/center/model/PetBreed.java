package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pet_breeds")
public class PetBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "species_id")
    private PetSpecies species;

    @OneToMany(mappedBy = "primaryBreed", fetch = FetchType.LAZY)
    private List<Pet> petsWithPrimaryBreed;

    @OneToMany(mappedBy = "secondaryBreed", fetch = FetchType.LAZY)
    private List<Pet> petsWithSecondaryBreed;

}
