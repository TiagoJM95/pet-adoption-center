package com.petadoption.center.testUtils;

import com.petadoption.center.model.*;
import com.petadoption.center.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestPersistenceHelper {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final OrganizationRepository organizationRepository;
    private final AdoptionFormRepository adoptionFormRepository;
    private final SpeciesRepository speciesRepository;
    private final BreedRepository breedRepository;
    private final ColorRepository colorRepository;
    private final InterestRepository interestRepository;

    @Autowired
    public TestPersistenceHelper(UserRepository userRepository, PetRepository petRepository, OrganizationRepository organizationRepository, AdoptionFormRepository adoptionFormRepository, SpeciesRepository speciesRepository, BreedRepository breedRepository, ColorRepository colorRepository, InterestRepository interestRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
        this.organizationRepository = organizationRepository;
        this.adoptionFormRepository = adoptionFormRepository;
        this.speciesRepository = speciesRepository;
        this.breedRepository = breedRepository;
        this.colorRepository = colorRepository;
        this.interestRepository = interestRepository;
    }

    public String persistTestUser() {
        User user = new User();
        userRepository.save(user);
        return user.getId();
    }

    public String persistTestPet() {
        Pet pet = new Pet();
        petRepository.save(pet);
        return pet.getId();
    }

    public String persistTestSpecies() {
        Species species = new Species();
        speciesRepository.save(species);
        return species.getId();
    }

    public String persistTestOrg() {
        Organization org = new Organization();
        organizationRepository.save(org);
        return org.getId();
    }

    public String persistTestAdoptionForm() {
        AdoptionForm adoptionForm = new AdoptionForm();
        adoptionFormRepository.save(adoptionForm);
        return adoptionForm.getId();
    }

    public String persistTestBreed(Species species) {
        Breed breed = Breed.builder()
                .name("Shepperd Doge")
                .species(species)
                .build();
        breedRepository.save(breed);
        return breed.getId();
    }

    public String persistTestColor() {
        Color color = new Color();
        colorRepository.save(color);
        return color.getId();
    }

    public String persistTestInterest() {
        Interest interest = new Interest();
        interestRepository.save(interest);
        return interest.getId();
    }

    public Species persistSpecies() {
        Species species = new Species();
        return speciesRepository.save(species);
    }
}
