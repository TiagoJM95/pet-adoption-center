package com.petadoption.center.model;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pets", uniqueConstraints = {
        @UniqueConstraint(name = "UniquePet", columnNames = {"name", "species_id", "primary_breed_id", "secondary_breed_id", "primary_color_id", "secondary_color_id", "tertiary_color_id", "gender", "coat", "size", "age", "description", "image_url", "is_adopted", "sterilized", "vaccinated", "chipped", "special_needs", "house_trained", "good_with_kids", "good_with_dogs", "good_with_cats", "date_added", "organization_id"})
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
    private Boolean isAdopted;

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

    @Column(name = "date_added")
    private LocalDate dateAdded;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "petId", fetch = FetchType.EAGER)
    private Set<AdoptionForm> petAdoptionForm;
}