package com.petadoption.center.specifications;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.Species;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecifications {

    private PetSpecifications() {}

    public static Specification<Pet> nameLike(String nameLike) {
        return (root, query, builder) -> builder.like(
                builder.lower(root.get("name")), "%" + nameLike + "%");
    }

    public static Specification<Pet> findByBreed(Breed breed) {
        return (root, query, builder) -> builder.or(
                builder.equal(root.get("primaryBreed") , breed),
                builder.equal(root.get("secondaryBreed") , breed));
    }

    public static Specification<Pet> findByColor(Color color) {
        return (root, query, builder) -> builder.or
                (builder.equal(root.get("primaryColor") , color),
                builder.equal(root.get("secondaryColor") , color),
                builder.equal(root.get("tertiaryColor") , color));
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
        return (root, query, builder) -> builder.equal(root.get("attributes").get("sterilized"), isSterilized);
    }

    public static Specification<Pet> isVaccinated(Boolean isVaccinated) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("vaccinated"), isVaccinated);
    }

    public static Specification<Pet> isChipped(Boolean isChipped) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("chipped"), isChipped);
    }

    public static Specification<Pet> isSpecialNeeds(Boolean isSpecialNeeds) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("specialNeeds"), isSpecialNeeds);
    }

    public static Specification<Pet> isHouseTrained(Boolean isHouseTrained) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("houseTrained"), isHouseTrained);
    }

    public static Specification<Pet> isGoodWithKids(Boolean isGoodWithKids) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("goodWithKids"), isGoodWithKids);
    }

    public static Specification<Pet> isGoodWithDogs(Boolean isGoodWithDogs) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("goodWithDogs"), isGoodWithDogs);
    }

    public static Specification<Pet> isGoodWithCats(Boolean isGoodWithCats) {
        return (root, query, builder) -> builder.equal(root.get("attributes").get("goodWithCats"), isGoodWithCats);
    }

    public static Specification<Pet> findBySpecies(Species species) {
        return (root, query, builder) -> builder.equal(root.get("species"), species);
    }

    public static Specification<Pet> findByState(String state) {
        return (root, query, builder) -> builder.equal(root.get("organization").get("state"), state);
    }

    public static Specification<Pet> findByCity(String city) {
        return (root, query, builder) -> builder.equal(root.get("organization").get("city"), city);
    }

    public static Specification<Pet> findByPureBreed(Boolean isPureBreed) {
        if(isPureBreed){
            return (root, query, builder) -> builder.equal(root.get("secondaryBreed") , null);
        }
        return null;
    }

}
