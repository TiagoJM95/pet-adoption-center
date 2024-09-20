package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.Family;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

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
    private User userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet petId;

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
}
