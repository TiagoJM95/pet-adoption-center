package com.petadoption.center.specifications;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.embeddable.Attributes;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PetSpecifications {

    private PetSpecifications() {}

    public static Specification<Pet> filterBySpecies(List<String> species) {
        return (root, query, builder) -> root.get("species").get("name").in(species);
    }

    public static Specification<Pet> filterByBreed(List<String> breeds) {
        return (root, query, builder) -> builder.or(
                root.get("primaryBreed").get("name").in(breeds),
                root.get("secondaryBreed").get("name").in(breeds)
        );
    }

    public static Specification<Pet> filterByColor(List<String> colors) {
        return (root, query, builder) -> builder.or(
                root.get("primaryColor").get("name").in(colors),
                root.get("secondaryColor").get("name").in(colors),
                root.get("tertiaryColor").get("name").in(colors)
        );
    }

    public static Specification<Pet> filterByGender(Genders genders) {
        return (root, query, builder) -> builder.equal(root.get("gender"), genders);
    }

    public static Specification<Pet> filterByCoat(List<Coats> coats) {
        return (root, query, builder) -> root.get("coat").in(coats);
    }

    public static Specification<Pet> filterBySize(List<Sizes> sizes) {
        return (root, query, builder) -> root.get("size").in(sizes);
    }

    public static Specification<Pet> filterByAge(List<Ages> ages) {
        return (root, query, builder) -> root.get("age").in(ages);
    }

    public static Specification<Pet> filterByIsAdopted(Boolean isAdopted) {
        return (root, query, builder) -> builder.equal(root.get("isAdopted"), isAdopted);
    }

    public static Specification<Pet> filterByAttributes(Attributes attributes) {
        return (root, query, builder) -> builder.equal(root.get("attributes"), attributes);
    }

    public static Specification<Pet> filterByState(List<String> states) {
        return (root, query, builder) -> root.get("organization").get("state").in(states);
    }

    public static Specification<Pet> filterByCity(List<String> cities) {
        return (root, query, builder) -> root.get("organization").get("city").in(cities);
    }

    public static Specification<Pet> filterByPureBreed() {
        return (root, query, builder) -> builder.equal(root.get("secondaryBreed") , null);
    }
}