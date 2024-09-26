package com.petadoption.center.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return Objects.equals(id, species.id) && Objects.equals(name, species.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}