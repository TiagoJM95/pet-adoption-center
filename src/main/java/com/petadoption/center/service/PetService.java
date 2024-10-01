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
import com.petadoption.center.model.*;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.interfaces.*;
import com.petadoption.center.specifications.PetSearchCriteria;
import jakarta.transaction.Transactional;
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
    public PetService(PetRepository petRepository, SpeciesServiceI speciesService, BreedServiceI breedService, ColorServiceI colorService, OrganizationServiceI organizationService) {
        this.petRepository = petRepository;
        this.speciesServiceI = speciesService;
        this.breedServiceI = breedService;
        this.colorServiceI = colorService;
        this.organizationServiceI = organizationService;
    }

    @Override
    public PetGetDto getPetById(String id) throws PetNotFoundException {
        return PetConverter.toDto(findPetById(id));
    }

    @Override
    public List<PetGetDto> searchPets(PetSearchCriteria criteria, int page, int size, String sortBy) throws PetDescriptionException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        Specification<Pet> filters = buildFilters(criteria);
        return petRepository.findAll(filters, pageRequest).stream().map(PetConverter::toDto).toList();
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto dto) throws SpeciesNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, OrgNotFoundException, PetDescriptionException {
        breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
        Pet pet = buildPetFromDto(dto);
        return PetConverter.toDto(petRepository.save(pet));
    }

    @Transactional
    @Override
    public void addListOfNewPets(List<PetCreateDto> pets) throws BreedNotFoundException, BreedMismatchException, OrgNotFoundException, PetDescriptionException, ColorNotFoundException, SpeciesNotFoundException {
        for (PetCreateDto dto : pets) {
            breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
            Pet pet = buildPetFromDto(dto);
            petRepository.save(pet);
        }
    }

    @Override
    public PetGetDto updatePet(String id, PetUpdateDto dto) throws PetNotFoundException, OrgNotFoundException, PetDescriptionException {
        Pet pet = findPetById(id);
        updatePetFields(dto, pet);
        petRepository.save(pet);
        return PetConverter.toDto(pet);
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

    private Pet buildPetFromDto(PetCreateDto dto) throws SpeciesNotFoundException, BreedNotFoundException, ColorNotFoundException, OrgNotFoundException, PetDescriptionException {
        Pet pet = PetConverter.toModel(dto);
        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesById(dto.petSpeciesId()));

        pet.setSpecies(species);
        pet.setPrimaryBreed(BreedConverter.toModel(breedServiceI.getBreedById(dto.primaryBreedId())));
        pet.setSecondaryBreed(getBreedOrNull(dto.secondaryBreedId(), species));
        pet.setPrimaryColor(ColorConverter.toModel(colorServiceI.getColorById(dto.primaryColor())));
        pet.setSecondaryColor(getColorOrNull(dto.secondaryColor()));
        pet.setTertiaryColor(getColorOrNull(dto.tertiaryColor()));
        pet.setGender(getGenderByDescription(dto.gender()));
        pet.setCoat(getCoatByDescription(dto.coat()));
        pet.setSize(getSizeByDescription(dto.size()));
        pet.setAge(getAgeByDescription(dto.age()));
        pet.setOrganization(OrgConverter.toModel(organizationServiceI.getOrganizationById(dto.organizationId())));
        return pet;
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
        updateFields(dto.isAdopted(), pet.isAdopted(), pet::setAdopted);
        updateFields(dto.attributes(), pet.getAttributes(), pet::setAttributes);
        updateFields(org, pet.getOrganization(), pet::setOrganization);
    }

    private Color getColorOrNull(String colorId) throws ColorNotFoundException {
        return colorId == null ? null : ColorConverter.toModel(colorServiceI.getColorById(colorId));
    }

    private Breed getBreedOrNull(String breedId, Species species) throws BreedNotFoundException {
        return breedId == null ? null : BreedConverter.toModel(breedServiceI.getBreedById(breedId));
    }
}