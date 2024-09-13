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
        @UniqueConstraint(name = "UniqueEmail", columnNames = {"email"}),
        @UniqueConstraint(name = "UniqueNif", columnNames = {"nif"}),
        @UniqueConstraint(name = "UniquePhoneNumber", columnNames = {"phone_number"}),
        @UniqueConstraint(name = "UniqueStreetAndPostalCode", columnNames = {"street", "postal_code"}),
        @UniqueConstraint(name = "UniqueWebsiteUrl", columnNames = {"website_url"}),
        @UniqueConstraint(name = "UniqueFacebook", columnNames = {"facebook"}),
        @UniqueConstraint(name = "UniqueInstagram", columnNames = {"instagram"}),
        @UniqueConstraint(name = "UniqueTwitter", columnNames = {"twitter"}),
        @UniqueConstraint(name = "UniqueYoutube", columnNames = {"youtube"})
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
    private List<Pet> petsOwned;
}