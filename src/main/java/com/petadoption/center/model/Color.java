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
@Table(name = "colors", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueColorName", columnNames = {"name"}),
})
public class Color {
    @Id
    @UuidGenerator
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Objects.equals(id, color.id) && Objects.equals(name, color.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}