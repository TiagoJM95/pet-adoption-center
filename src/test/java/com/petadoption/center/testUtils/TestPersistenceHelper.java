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
    private Color primaryColor;
    private Organization organization;

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

    public String persistTestPrimaryBreed() {
        primaryBreed = Breed.builder()
                .name("Shepperd Doge")
                .species(species)
                .build();
        breedRepository.save(primaryBreed);
        return primaryBreed.getId();
    }

    public String persistTestPrimaryColor() {
        primaryColor = createPrimaryColorWithoutId();
        colorRepository.save(primaryColor);
        return primaryColor.getId();
    }

    public void cleanAll() {
        interestRepository.deleteAll();
        adoptionFormRepository.deleteAll();
        petRepository.deleteAll();
        breedRepository.deleteAll();
        speciesRepository.deleteAll();
        organizationRepository.deleteAll();
        colorRepository.deleteAll();
        userRepository.deleteAll();
    }
}
