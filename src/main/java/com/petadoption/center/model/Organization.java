package com.petadoption.center.model;

import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "organizations", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueOrgEmail", columnNames = {"email"}),
        @UniqueConstraint(name = "UniqueOrgNipc", columnNames = {"nipc"}),
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
    private String nipc;

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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(nipc, that.nipc) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(address, that.address) && Objects.equals(websiteUrl, that.websiteUrl) && Objects.equals(socialMedia, that.socialMedia) && Objects.equals(petsOwned, that.petsOwned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, nipc, phoneNumber, address, websiteUrl, socialMedia, petsOwned);
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}