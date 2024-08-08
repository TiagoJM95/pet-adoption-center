package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private Address address;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "social_media")
    private SocialMedia socialMedia;
}
