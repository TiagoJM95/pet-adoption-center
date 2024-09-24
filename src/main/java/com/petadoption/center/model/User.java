package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Interest> interests = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(nif, user.nif) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(address, user.address) && Objects.equals(favoritePets, user.favoritePets) && Objects.equals(adoptedPets, user.adoptedPets) && Objects.equals(interests, user.interests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, nif, phoneNumber, dateOfBirth, address, favoritePets, adoptedPets, interests);
    }
}