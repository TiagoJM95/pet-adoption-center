package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.petadoption.center.util.Utils.truncateToMicros;

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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return Objects.equals(id, breed.id) && Objects.equals(name, breed.name) && Objects.equals(species, breed.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, species);
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = truncateToMicros(LocalDateTime.now());
        }
    }
}