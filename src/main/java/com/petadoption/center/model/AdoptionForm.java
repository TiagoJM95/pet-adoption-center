package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "adoption_forms", uniqueConstraints = @UniqueConstraint(name = "UniqueUserAndPet",columnNames = {"user_id", "pet_id"}))
public class AdoptionForm {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "familyCount", column = @Column(name = "family_count")),
            @AttributeOverride( name = "likesPets", column = @Column(name = "family_likes_pets")),
            @AttributeOverride( name = "hasOtherPets", column = @Column(name = "family_has_pets")),
            @AttributeOverride( name = "numberOfPets", column = @Column(name = "family_pet_count")),
            @AttributeOverride( name = "familyPets", column = @Column(name = "family_pet_list"))
    })
    private Family userFamily;

    private String petVacationHome;

    private Boolean isResponsibleForPet;

    private String otherNotes;

    private Address petAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptionForm that = (AdoptionForm) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(pet, that.pet) && Objects.equals(userFamily, that.userFamily) && Objects.equals(petVacationHome, that.petVacationHome) && Objects.equals(isResponsibleForPet, that.isResponsibleForPet) && Objects.equals(otherNotes, that.otherNotes) && Objects.equals(petAddress, that.petAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, pet, userFamily, petVacationHome, isResponsibleForPet, otherNotes, petAddress);
    }
}
