package com.petadoption.center.repository;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByName(String name);

    List<Pet> findBySpecies(Species species);

    List<Pet> findByPrimaryBreed(Breed breed);

    List<Pet> findBySecondaryBreed(Breed breed);

    @Query("SELECT p FROM Pet p WHERE p.primaryBreed = :breed OR p.secondaryBreed = :breed")
    List<Pet> findByBreed(Breed breed);

    List<Pet> findByPrimaryColor(Color color);

    List<Pet> findBySecondaryColor(Color color);

    List<Pet> findByTertiaryColor(Color color);

    @Query("SELECT p FROM Pet p WHERE p.primaryColor = :color OR p.secondaryColor = :color OR p.tertiaryColor = :color")
    List<Pet> findByColor(Color color);

    List<Pet> findByGender(Genders gender);

    List<Pet> findByCoat(Coats coat);

    List<Pet> findBySize(Sizes size);

    List<Pet> findByAge(Ages age);

    List<Pet> findByIsAdopted(Boolean isAdopted);

    List<Pet> findByAttributes_Sterilized(Boolean sterilized);

    List<Pet> findByAttributes_Vaccinated(Boolean vaccinated);

    List<Pet> findByAttributes_Chipped(Boolean chipped);

    List<Pet> findByAttributes_SpecialNeeds(Boolean specialNeeds);

    List<Pet> findByAttributes_HouseTrained(Boolean houseTrained);

    List<Pet> findByAttributes_GoodWithKids(Boolean goodWithKids);

    List<Pet> findByAttributes_GoodWithDogs(Boolean goodWithDogs);

    List<Pet> findByAttributes_GoodWithCats(Boolean goodWithCats);

}
