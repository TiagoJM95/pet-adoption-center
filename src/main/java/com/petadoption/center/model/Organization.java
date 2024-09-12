package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "organizations", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueStreetAndPostalCode", columnNames = {"street", "postal_code"})
})
public class Organization {

    @Id
    @UuidGenerator
    private String id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nif;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "street")),
            @AttributeOverride( name = "city", column = @Column(name = "city")),
            @AttributeOverride( name = "state", column = @Column(name = "state")),
            @AttributeOverride( name = "postalCode", column = @Column(name = "postal_code"))
    })
    private Address address;

    @Column(name = "website_url", unique = true)
    private String websiteUrl;

    @Embedded
    @Column(name = "social_media", unique = true)
    private SocialMedia socialMedia;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
    @Column(name = "pets_owned")
    private List<Pet> petsOwned;
}