package com.petadoption.center.model;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.embeddable.Attributes;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
            @AttributeOverride( name = "sterilized", column = @Column(name = "pet_sterilized")),
            @AttributeOverride( name = "vaccinated", column = @Column(name = "pet_vaccinated")),
            @AttributeOverride( name = "chipped", column = @Column(name = "pet_chipped")),
            @AttributeOverride( name = "specialNeeds", column = @Column(name = "pet_special_needs")),
            @AttributeOverride( name = "houseTrained", column = @Column(name = "pet_house_trained")),
            @AttributeOverride( name = "goodWithKids", column = @Column(name = "pet_good_with_kids")),
            @AttributeOverride( name = "goodWithDogs", column = @Column(name = "pet_good_with_dogs")),
            @AttributeOverride( name = "goodWithCats", column = @Column(name = "pet_good_with_cats"))
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
