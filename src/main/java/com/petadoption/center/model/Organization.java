package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "street", column = @Column(name = "street")),
            @AttributeOverride( name = "city", column = @Column(name = "city")),
            @AttributeOverride( name = "state", column = @Column(name = "state")),
            @AttributeOverride( name = "postalCode", column = @Column(name = "postal_code"))
    })
    private Address address;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "social_media")
    private SocialMedia socialMedia;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    @Column(name = "pets_owned")
    private List<Pet> petsOwned;
}
