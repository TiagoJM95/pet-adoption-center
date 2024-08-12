package com.petadoption.center.repository;

import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByName(String name);

    List<Optional<Pet>> findBySpecies(Species species);

    List<Optional<Pet>> findByPrimaryBreed(Breed breed);

    List<Optional<Pet>> findBySecondaryBreed(Breed breed);

    List<Pet> findByPrimaryBreedOrSecondaryBreed(Breed breed);

    List<Optional<Pet>> findByPrimaryColor(Color color);

    List<Optional<Pet>> findBySecondaryColor(Color color);

    List<Optional<Pet>> findByTertiaryColor(Color color);

    List<Optional<Pet>> findByPrimaryColorOrSecondaryColorOrTertiaryColor(Color color);

    List<Optional<Pet>> findByGender(Genders gender);

    List<Optional<Pet>> findByCoat(Coats coat);

    List<Optional<Pet>> findBySize(Sizes size);

    List<Optional<Pet>> findByAge(Ages age);

    List<Optional<Pet>> findByIsAdopted(Boolean isAdopted);

    List<Optional<Pet>> findBySterilized(Boolean sterilized);

    List<Optional<Pet>> findByVaccinated(Boolean vaccinated);

    List<Optional<Pet>> findByChipped(Boolean chipped);

    List<Optional<Pet>> findBySpecialNeeds(Boolean specialNeeds);

    List<Optional<Pet>> findByHouseTrained(Boolean houseTrained);

    List<Optional<Pet>> findByGoodWithKids(Boolean goodWithKids);

    List<Optional<Pet>> findByGoodWithDogs(Boolean goodWithDogs);

    List<Optional<Pet>> findByGoodWithCats(Boolean goodWithCats);

}
