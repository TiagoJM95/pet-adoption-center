package com.petadoption.center.specifications;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Pet;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecifications {

    private PetSpecifications() {}

    public static Specification<Pet> nameLike(String nameLike) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + nameLike + "%");
    }

    public static Specification<Pet> findByBreed(Breed breed) {
        return (root, query, builder) -> builder.or(builder.equal(root.get("primaryBreed") , breed), builder.equal(root.get("secondaryBreed") , breed));
    }

    public static Specification<Pet> findByPrimaryBreed(Breed breed) {
        return (root, query, builder) -> builder.equal(root.get("primaryBreed") , breed);
    }

    public static Specification<Pet> findBySecondaryBreed(Breed breed) {
        return (root, query, builder) -> builder.equal(root.get("secondaryBreed") , breed);
    }

    public static Specification<Pet> findByColor(Color color) {
        return (root, query, builder) -> builder.or
                (builder.equal(root.get("primaryColor") , color),
                builder.equal(root.get("secondaryColor") , color),
                builder.equal(root.get("tertiaryColor") , color));
    }

    public static Specification<Pet> findByPrimaryColor(Color color) {
        return (root, query, builder) -> builder.equal(root.get("primaryColor") , color);
    }
    public static Specification<Pet> findBySecondaryColor(Color color) {
        return (root, query, builder) -> builder.equal(root.get("secondaryColor") , color);
    }
    public static Specification<Pet> findByTertiaryColor(Color color) {
        return (root, query, builder) -> builder.equal(root.get("tertiaryColor") , color);
    }

    public static Specification<Pet> findByGender(Genders genders) {
        return (root, query, builder) -> builder.equal(root.get("gender"), genders);
    }

    public static Specification<Pet> findByCoat(Coats coats) {
        return (root, query, builder) -> builder.equal(root.get("coat"), coats);
    }
    public static Specification<Pet> findBySize(Sizes sizes) {
        return (root, query, builder) -> builder.equal(root.get("size"), sizes);
    }
    public static Specification<Pet> findByAge(Ages ages) {
        return (root, query, builder) -> builder.equal(root.get("age"), ages);
    }

    public static Specification<Pet> isAdopted(Boolean isAdopted) {
        return (root, query, builder) -> builder.equal(root.get("isAdopted"), isAdopted);
    }

    public static Specification<Pet> isSterilized(Boolean isSterilized) {
        return (root, query, builder) -> builder.equal(root.get("pet_sterilized"), isSterilized);
    }

}
