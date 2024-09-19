package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserEmail", columnNames = {"email"}),
        @UniqueConstraint(name = "UniqueUserNif", columnNames = {"nif"}),
        @UniqueConstraint(name = "UniqueUserPhoneNumber", columnNames = {"phone_number"})
})
public class User {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String nif;

    @Column(name = "phone_number")
    private String phoneNumber;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favorite_pets",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "pets_id")
    )
    private Set<Pet> favoritePets = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_adopted_pets",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "pets_id")
    )
    private Set<Pet> adoptedPets = new HashSet<>();

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private Set<AdoptionForm> userAdoptionForms = new HashSet<>();

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    @Column(name = "interests_in_pets")
    private Set<Interest> interests = new HashSet<>();
}