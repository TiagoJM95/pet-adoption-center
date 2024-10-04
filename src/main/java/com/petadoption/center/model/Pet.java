package com.petadoption.center.model;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pets", uniqueConstraints = {
        @UniqueConstraint(name = "UniquePet", columnNames = {"name", "species_id", "primary_breed_id", "secondary_breed_id", "primary_color_id", "secondary_color_id", "tertiary_color_id", "gender", "coat", "size", "age", "description", "image_url", "is_adopted", "sterilized", "vaccinated", "chipped", "special_needs", "house_trained", "good_with_kids", "good_with_dogs", "good_with_cats", "created_at", "organization_id"})
})
public class Pet {

    @Id
    @UuidGenerator
    private String id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "species_id")
    private Species species;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_breed_id")
    private Breed primaryBreed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondary_breed_id")
    private Breed secondaryBreed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "primary_color_id")
    private Color primaryColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondary_color_id")
    private Color secondaryColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tertiary_color_id")
    private Color tertiaryColor;

    @Enumerated(EnumType.STRING)
    private Genders gender;

    @Enumerated(EnumType.STRING)
    private Coats coat;

    @Enumerated(EnumType.STRING)
    private Sizes size;

    @Enumerated(EnumType.STRING)
    private Ages age;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;
    private boolean isAdopted;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "sterilized", column = @Column(name = "sterilized")),
            @AttributeOverride( name = "vaccinated", column = @Column(name = "vaccinated")),
            @AttributeOverride( name = "chipped", column = @Column(name = "chipped")),
            @AttributeOverride( name = "specialNeeds", column = @Column(name = "special_needs")),
            @AttributeOverride( name = "houseTrained", column = @Column(name = "house_trained")),
            @AttributeOverride( name = "goodWithKids", column = @Column(name = "good_with_kids")),
            @AttributeOverride( name = "goodWithDogs", column = @Column(name = "good_with_dogs")),
            @AttributeOverride( name = "goodWithCats", column = @Column(name = "good_with_cats"))
    })
    private Attributes attributes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return isAdopted == pet.isAdopted && Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(species, pet.species) && Objects.equals(primaryBreed, pet.primaryBreed) && Objects.equals(secondaryBreed, pet.secondaryBreed) && Objects.equals(primaryColor, pet.primaryColor) && Objects.equals(secondaryColor, pet.secondaryColor) && Objects.equals(tertiaryColor, pet.tertiaryColor) && gender == pet.gender && coat == pet.coat && size == pet.size && age == pet.age && Objects.equals(description, pet.description) && Objects.equals(imageUrl, pet.imageUrl) && Objects.equals(attributes, pet.attributes) && Objects.equals(createdAt, pet.createdAt) && Objects.equals(organization, pet.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, species, primaryBreed, secondaryBreed, primaryColor, secondaryColor, tertiaryColor, gender, coat, size, age, description, imageUrl, isAdopted, attributes, createdAt, organization);
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}