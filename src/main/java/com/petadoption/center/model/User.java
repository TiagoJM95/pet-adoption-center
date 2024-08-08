package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone_country_code")
    private String phoneCountryCode;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @Column(name = "favorite_pets")
    private List<Pet> favoritePets;

    @Column(name = "adopted_pets")
    private List<Pet> adoptedPets;

    @Column(name = "adoption_forms")
    private List<AdoptionForm> adoptionForms;
}
