package com.petadoption.center.service;

import com.petadoption.center.converter.*;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDescriptionException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.interfaces.*;
import com.petadoption.center.specifications.PetSearchCriteria;
import com.petadoption.center.service.aggregator.PetCreateContext;
import com.petadoption.center.service.aggregator.PetGetContext;
import com.petadoption.center.factory.AttributesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.enums.Ages.getAgeByDescription;
import static com.petadoption.center.enums.Coats.getCoatByDescription;
import static com.petadoption.center.enums.Genders.getGenderByDescription;
import static com.petadoption.center.enums.Sizes.getSizeByDescription;
import static com.petadoption.center.specifications.PetSpecifications.*;
import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Utils.updateFields;

@Service
public class PetService implements PetServiceI {

    private final PetRepository petRepository;
    private final SpeciesServiceI speciesServiceI;
    private final BreedServiceI breedServiceI;
    private final ColorServiceI colorServiceI;
    private final OrganizationServiceI organizationServiceI;

    @Autowired
    public PetService(PetRepository petRepository, SpeciesService speciesService, BreedService breedService, ColorService colorService, OrganizationService organizationService) {
        this.petRepository = petRepository;
        this.speciesServiceI = speciesService;
        this.breedServiceI = breedService;
        this.colorServiceI = colorService;
        this.organizationServiceI = organizationService;
    }

    @Override
    public PetGetDto getPetById(String id) throws PetNotFoundException {
        return PetConverter.toDto(findPetById(id), buildGetContext(findPetById(id)));
    }

    @Override
    public List<PetGetDto> searchPets(PetSearchCriteria criteria, int page, int size, String sortBy) throws PetDescriptionException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        Specification<Pet> filters = buildFilters(criteria);
        return petRepository.findAll(filters, pageRequest).stream().map(pet -> PetConverter.toDto(pet, buildGetContext(pet))).toList();
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto dto) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException, PetDescriptionException {
        breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
        Pet p = PetConverter.toModel(dto, buildCreateContext(dto));
        Pet pet = petRepository.save(p);
        return PetConverter.toDto(pet, buildGetContext(pet));
    }

    @Override
    public void addListOfNewPets(List<PetCreateDto> pets) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException, PetDescriptionException {
        for (PetCreateDto dto : pets) {
            breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
        }
        for (PetCreateDto dto : pets) {
            petRepository.save(PetConverter.toModel(dto, buildCreateContext(dto)));
        }
    }

    @Override
    public PetGetDto updatePet(String id, PetUpdateDto dto) throws PetNotFoundException, OrgNotFoundException, PetDescriptionException {
        Pet pet = findPetById(id);
        updatePetFields(dto, pet);
        petRepository.save(pet);
        return PetConverter.toDto(pet, buildGetContext(pet));
    }

    @Override
    public String deletePet(String id) throws PetNotFoundException {
        findPetById(id);
        petRepository.deleteById(id);
        return PET_WITH_ID + id + DELETE_SUCCESS;
    }

    private Pet findPetById(String id) throws PetNotFoundException {
        return petRepository.findById(id).orElseThrow(
                () -> new PetNotFoundException(PET_WITH_ID + id + NOT_FOUND));
    }

    private PetGetContext buildGetContext(Pet pet){
        return PetGetContext.builder()
                .organization(OrgConverter.toDto(pet.getOrganization()))
                .species(pet.getSpecies().getName())
                .primaryBreed(pet.getPrimaryBreed().getName())
                .secondaryBreed(pet.getSecondaryBreed() != null
                        ? pet.getSecondaryBreed().getName()
                        : "NONE")
                .primaryColor(pet.getPrimaryColor().getName())
                .secondaryColor(pet.getSecondaryColor() != null
                        ? pet.getSecondaryColor().getName()
                        : "NONE")
                .tertiaryColor(pet.getTertiaryColor() != null
                        ? pet.getTertiaryColor().getName()
                        : "NONE")
                .build();
    }

    private PetCreateContext buildCreateContext(PetCreateDto pet) throws SpeciesNotFoundException, BreedNotFoundException, ColorNotFoundException, OrgNotFoundException, PetDescriptionException {

        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesById(pet.petSpeciesId()));
        Breed primaryBreed = BreedConverter.toModel(breedServiceI.getBreedById(pet.primaryBreedId()), species);
        Breed secondaryBreed = pet.secondaryBreedId().equals("NONE")
                ? null
                :  BreedConverter.toModel(breedServiceI.getBreedById(pet.secondaryBreedId()), species);


        return PetCreateContext.builder()
                .species(species)
                .primaryBreed(primaryBreed)
                .secondaryBreed(secondaryBreed)
                .primaryColor(ColorConverter.toModel(colorServiceI.getColorById(pet.primaryColor())))
                .secondaryColor(pet.secondaryColor().equals("NONE")
                        ? null
                        : ColorConverter.toModel(colorServiceI.getColorById(pet.secondaryColor())))
                .tertiaryColor(pet.tertiaryColor().equals("NONE")
                        ? null
                        : ColorConverter.toModel(colorServiceI.getColorById(pet.tertiaryColor())))
                .organization(OrgConverter.toModel(organizationServiceI.getOrganizationById(pet.organizationId())))
                .gender(getGenderByDescription(pet.gender()))
                .coat(getCoatByDescription(pet.coat()))
                .size(getSizeByDescription(pet.size()))
                .age(getAgeByDescription(pet.age()))
                .attributes(AttributesFactory.create(pet))
                .build();
    }

    private Specification<Pet> buildFilters(PetSearchCriteria searchCriteria) throws PetDescriptionException {
        return Specification.where(
                         nameLike(searchCriteria.nameLike().toLowerCase())
                        .and(findBySpecies(searchCriteria.species()))
                        .and(findByState(searchCriteria.state()))
                        .and(findByCity(searchCriteria.city()))
                        .and(findByBreed((searchCriteria.breed())))
                        .and(findByColor(searchCriteria.color()))
                        .and(findByGender(getGenderByDescription(searchCriteria.gender())))
                        .and(findByCoat(getCoatByDescription(searchCriteria.coat())))
                        .and(findBySize(getSizeByDescription(searchCriteria.size())))
                        .and(findByAge(getAgeByDescription(searchCriteria.age())))
                        .and(isAdopted(searchCriteria.isAdopted()))
                        .and(isSterilized(searchCriteria.isSterilized()))
                        .and(isVaccinated(searchCriteria.isVaccinated()))
                        .and(isChipped(searchCriteria.isChipped()))
                        .and(isSpecialNeeds(searchCriteria.isSpecialNeeds()))
                        .and(isHouseTrained(searchCriteria.isHouseTrained()))
                        .and(isGoodWithKids(searchCriteria.goodWithKids()))
                        .and(isGoodWithDogs(searchCriteria.goodWithDogs()))
                        .and(isGoodWithCats(searchCriteria.goodWithCats()))
                        .and(findByPureBreed(searchCriteria.isPureBreed()))
                );
    }

    private void updatePetFields(PetUpdateDto dto, Pet pet) throws OrgNotFoundException, PetDescriptionException {
        Organization org = OrgConverter.toModel(organizationServiceI.getOrganizationById(dto.organizationId()));

        updateFields(getSizeByDescription(dto.size()), pet.getSize(), pet::setSize);
        updateFields(getAgeByDescription(dto.age()), pet.getAge(), pet::setAge);
        updateFields(dto.description(), pet.getDescription(), pet::setDescription);
        updateFields(dto.imageUrl(), pet.getImageUrl(), pet::setImageUrl);
        updateFields(dto.isAdopted(), pet.getIsAdopted(), pet::setIsAdopted);
        updateFields(AttributesFactory.create(dto), pet.getAttributes(), pet::setAttributes);
        updateFields(org, pet.getOrganization(), pet::setOrganization);
    }
}