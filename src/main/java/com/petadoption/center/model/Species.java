package com.petadoption.center.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "species", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueSpeciesName", columnNames = {"name"}),
})
public class Species {
    @Id
    @UuidGenerator
    private String id;
    private String name;
}