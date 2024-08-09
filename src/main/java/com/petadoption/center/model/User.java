package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "street")),
            @AttributeOverride( name = "city", column = @Column(name = "city")),
            @AttributeOverride( name = "state", column = @Column(name = "state")),
            @AttributeOverride( name = "postalCode", column = @Column(name = "postal_code"))
    })
    private Address address;

    @Column(name = "phone_country_code")
    private String phoneCountryCode;

    @Column(name = "phone_number")
    private Integer phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favorite_pets",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "pets_id")
    )
    private Set<Pet> favoritePets;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_adopted_pets",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "pets_id")
    )
    private Set<Pet> adoptedPets;

    @OneToMany(mappedBy = "user_id", fetch = FetchType.EAGER)
    private Set<AdoptionForm> userAdoptionForms;
}
