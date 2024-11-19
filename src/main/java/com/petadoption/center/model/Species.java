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
@Table(name = "species", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueSpeciesName", columnNames = {"name"}),
})
public class Species {
    @Id
    @UuidGenerator
    private String id;
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

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

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = truncateToMicros(LocalDateTime.now());
        }
    }
}