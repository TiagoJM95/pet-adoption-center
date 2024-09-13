package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "breeds", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueBreedName", columnNames = {"name"}),
})
public class Breed {
    @Id
    @UuidGenerator
    private String id;
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "species_id")
    private Species species;
}