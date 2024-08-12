package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "primaryColor", fetch = FetchType.LAZY)
    private List<Pet> petsWithPrimaryColor;

    @OneToMany(mappedBy = "secondaryColor", fetch = FetchType.LAZY)
    private List<Pet> petsWithSecondaryColor;

    @OneToMany(mappedBy = "tertiaryColor", fetch = FetchType.LAZY)
    private List<Pet> petsWithTertiaryColor;
}
