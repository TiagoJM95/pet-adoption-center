package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "adoption_forms")
public class AdoptionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "userAdoptionForms", fetch = FetchType.EAGER)
    private List<User> users;

    @OneToMany(mappedBy = "petAdoptionForm", fetch = FetchType.EAGER)
    private List<Pet> pets;
}
