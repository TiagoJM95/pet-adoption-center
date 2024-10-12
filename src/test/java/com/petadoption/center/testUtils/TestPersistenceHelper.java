package com.petadoption.center.testUtils;

import com.petadoption.center.model.*;
import com.petadoption.center.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.petadoption.center.testUtils.TestEntityFactory.*;

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

    private Species species;
    private Breed primaryBreed;
    private Breed secondaryBreed;
    private Color primaryColor;
    private Color secondaryColor;
    private Color tertiaryColor;
    private Pet pet;
    private User user;
    private Organization organization;
    private AdoptionForm adoptionForm;
    private Interest interest;

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
        user = createUserWithoutId();
        userRepository.save(user);
        return user.getId();
    }

    public String persistTestPet() {
        pet = createPetWithoutId();
        pet.setSpecies(species);
        pet.setPrimaryBreed(primaryBreed);
        pet.setSecondaryBreed(secondaryBreed);
        pet.setPrimaryColor(primaryColor);
        pet.setSecondaryColor(secondaryColor);
        pet.setTertiaryColor(tertiaryColor);
        pet.setOrganization(organization);
        petRepository.save(pet);
        return pet.getId();
    }

    public String persistTestSpecies() {
        species = createSpeciesWithoutId();
        speciesRepository.save(species);
        return species.getId();
    }

    public String persistTestOrg() {
        organization = createOrganizationWithoutId();
        organizationRepository.save(organization);
        return organization.getId();
    }

    public String persistTestAdoptionForm() {
        AdoptionForm adoptionForm = new AdoptionForm();
        adoptionFormRepository.save(adoptionForm);
        return adoptionForm.getId();
    }

    public String persistTestPrimaryBreed() {
        primaryBreed = Breed.builder()
                .name("Shepperd Doge")
                .species(species)
                .build();
        breedRepository.save(primaryBreed);
        return primaryBreed.getId();
    }

    public String persistTestSecondaryBreed() {
        secondaryBreed = Breed.builder()
                .name("Labrador")
                .species(species)
                .build();
        breedRepository.save(secondaryBreed);
        return secondaryBreed.getId();
    }

    public String persistTestPrimaryColor() {
        primaryColor = createPrimaryColorWithoutId();
        colorRepository.save(primaryColor);
        return primaryColor.getId();
    }

    public String persistTestSecondaryColor() {
        secondaryColor = createSecondaryColorWithoutId();
        colorRepository.save(secondaryColor);
        return secondaryColor.getId();
    }

    public String persistTestTertiaryColor() {
        tertiaryColor = createTertiaryColorWithoutId();
        colorRepository.save(tertiaryColor);
        return tertiaryColor.getId();
    }

    public String persistTestInterest() {
        Interest interest = new Interest();
        interestRepository.save(interest);
        return interest.getId();
    }
}
