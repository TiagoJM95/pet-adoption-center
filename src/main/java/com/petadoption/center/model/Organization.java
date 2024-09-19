package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "organizations", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueOrgEmail", columnNames = {"email"}),
        @UniqueConstraint(name = "UniqueOrgNif", columnNames = {"nif"}),
        @UniqueConstraint(name = "UniqueOrgPhoneNumber", columnNames = {"phone_number"}),
        @UniqueConstraint(name = "UniqueOrgStreetAndPostalCode", columnNames = {"street", "postal_code"}),
        @UniqueConstraint(name = "UniqueOrgWebsiteUrl", columnNames = {"website_url"}),
        @UniqueConstraint(name = "UniqueOrgFacebook", columnNames = {"facebook"}),
        @UniqueConstraint(name = "UniqueOrgInstagram", columnNames = {"instagram"}),
        @UniqueConstraint(name = "UniqueOrgTwitter", columnNames = {"twitter"}),
        @UniqueConstraint(name = "UniqueOrgYoutube", columnNames = {"youtube"})
})
public class Organization {

    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String email;
    private String nif;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "facebook", column = @Column(name = "facebook")),
            @AttributeOverride(name = "instagram", column = @Column(name = "instagram")),
            @AttributeOverride(name = "twitter", column = @Column(name = "twitter")),
            @AttributeOverride(name = "youtube", column = @Column(name = "youtube"))
    })
    private SocialMedia socialMedia;

    @OneToMany(mappedBy = "organization", fetch = FetchType.EAGER)
    @Column(name = "pets_owned")
    private List<Pet> petsOwned = new ArrayList<>();

    @OneToMany(mappedBy = "organizationId", fetch = FetchType.EAGER)
    @Column(name = "interests_in_owned_pets")
    private Set<Interest> interests = new HashSet<>();
}