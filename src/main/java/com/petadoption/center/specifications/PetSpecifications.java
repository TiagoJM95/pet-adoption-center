package com.petadoption.center.specifications;

import com.petadoption.center.model.Pet;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecifications {

    private PetSpecifications() {}

    public static Specification<Pet> nameLike(String nameLike) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + nameLike + "%");
    }
}
