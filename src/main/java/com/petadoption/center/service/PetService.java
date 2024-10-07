package com.petadoption.center.service;

import com.petadoption.center.converter.*;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.BreedMismatchException;
import com.petadoption.center.exception.not_found.BreedNotFoundException;
import com.petadoption.center.exception.not_found.ColorNotFoundException;
import com.petadoption.center.exception.not_found.OrganizationNotFoundException;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.exception.not_found.SpeciesNotFoundException;
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

import static com.petadoption.center.converter.EnumConverter.convertStringToEnum;
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
    public List<PetGetDto> searchPets(PetSearchCriteria criteria, int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        Specification<Pet> filters = buildFilters(criteria);
        return petRepository.findAll(filters, pageRequest).stream().map(PetConverter::toDto).toList();
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto dto) throws SpeciesNotFoundException, BreedNotFoundException, BreedMismatchException, ColorNotFoundException, OrganizationNotFoundException {
        breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
        Pet pet = buildPetFromDto(dto);
        return PetConverter.toDto(petRepository.save(pet));
    }

    @Transactional
    @Override
    public void addListOfNewPets(List<PetCreateDto> pets) throws BreedNotFoundException, BreedMismatchException, OrganizationNotFoundException, ColorNotFoundException, SpeciesNotFoundException {
        for (PetCreateDto dto : pets) {
            breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
            Pet pet = buildPetFromDto(dto);
            petRepository.save(pet);
        }
    }

    @Override
    public PetGetDto updatePet(String id, PetUpdateDto dto) throws PetNotFoundException, OrganizationNotFoundException {
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

    private Pet buildPetFromDto(PetCreateDto dto) throws SpeciesNotFoundException, BreedNotFoundException, ColorNotFoundException, OrganizationNotFoundException {
        Pet pet = PetConverter.toModel(dto);
        Species species = SpeciesConverter.toModel(speciesServiceI.getSpeciesById(dto.petSpeciesId()));

        pet.setSpecies(species);
        pet.setPrimaryBreed(BreedConverter.toModel(breedServiceI.getById(dto.primaryBreedId())));
        pet.setSecondaryBreed(getBreedOrNull(dto.secondaryBreedId(), species));
        pet.setPrimaryColor(ColorConverter.toModel(colorServiceI.getById(dto.primaryColor())));
        pet.setSecondaryColor(getColorOrNull(dto.secondaryColor()));
        pet.setTertiaryColor(getColorOrNull(dto.tertiaryColor()));
        pet.setGender(convertStringToEnum(dto.gender(), Genders.class));
        pet.setCoat(convertStringToEnum(dto.coat(), Coats.class));
        pet.setSize(convertStringToEnum(dto.size(), Sizes.class));
        pet.setAge(convertStringToEnum(dto.age(), Ages.class));
        pet.setOrganization(OrganizationConverter.toModel(organizationServiceI.getById(dto.organizationId())));
        return pet;
    }

    private Specification<Pet> buildFilters(PetSearchCriteria searchCriteria) {
        return Specification.where(searchCriteria.nameLike() != null && !searchCriteria.nameLike().isEmpty() ? nameLike(searchCriteria.nameLike()) : null)
                .and(searchCriteria.species() != null ? findBySpecies(searchCriteria.species()) : null)
                .and(searchCriteria.state() != null ? findByState(searchCriteria.state()) : null)
                .and(searchCriteria.city() != null ? findByCity(searchCriteria.city()) : null)
                .and(searchCriteria.breed() != null ? findByBreed(searchCriteria.breed()) : null)
                .and(searchCriteria.color() != null ? findByColor(searchCriteria.color()) : null)
                .and(searchCriteria.gender() != null ? findByGender(convertStringToEnum(searchCriteria.gender(), Genders.class)) : null)
                .and(searchCriteria.coat() != null ? findByCoat(convertStringToEnum(searchCriteria.coat(), Coats.class)) : null)
                .and(searchCriteria.size() != null ? findBySize(convertStringToEnum(searchCriteria.size(), Sizes.class)) : null)
                .and(searchCriteria.age() != null ? findByAge(convertStringToEnum(searchCriteria.age(), Ages.class)) : null)
                .and(searchCriteria.isAdopted() != null ? isAdopted(searchCriteria.isAdopted()) : null)
                .and(searchCriteria.isSterilized() != null ? isSterilized(searchCriteria.isSterilized()) : null)
                .and(searchCriteria.isVaccinated() != null ? isVaccinated(searchCriteria.isVaccinated()) : null)
                .and(searchCriteria.isChipped() != null ? isChipped(searchCriteria.isChipped()) : null)
                .and(searchCriteria.isSpecialNeeds() != null ? isSpecialNeeds(searchCriteria.isSpecialNeeds()) : null)
                .and(searchCriteria.isHouseTrained() != null ? isHouseTrained(searchCriteria.isHouseTrained()) : null)
                .and(searchCriteria.goodWithKids() != null ? isGoodWithKids(searchCriteria.goodWithKids()) : null)
                .and(searchCriteria.goodWithDogs() != null ? isGoodWithDogs(searchCriteria.goodWithDogs()) : null)
                .and(searchCriteria.goodWithCats() != null ? isGoodWithCats(searchCriteria.goodWithCats()) : null)
                .and(searchCriteria.isPureBreed() != null ? findByPureBreed(searchCriteria.isPureBreed()) : null);
    }

    private void updatePetFields(PetUpdateDto dto, Pet pet) throws OrganizationNotFoundException {
        Organization org = OrganizationConverter.toModel(organizationServiceI.getById(dto.organizationId()));

        updateFields(convertStringToEnum(dto.size(), Sizes.class), pet.getSize(), pet::setSize);
        updateFields(convertStringToEnum(dto.age(), Ages.class), pet.getAge(), pet::setAge);
        updateFields(dto.description(), pet.getDescription(), pet::setDescription);
        updateFields(dto.imageUrl(), pet.getImageUrl(), pet::setImageUrl);
        updateFields(dto.isAdopted(), pet.isAdopted(), pet::setAdopted);
        updateFields(dto.attributes(), pet.getAttributes(), pet::setAttributes);
        updateFields(org, pet.getOrganization(), pet::setOrganization);
    }

    private Color getColorOrNull(String colorId) throws ColorNotFoundException {
        return colorId == null ? null : ColorConverter.toModel(colorServiceI.getById(colorId));
    }

    private Breed getBreedOrNull(String breedId, Species species) throws BreedNotFoundException {
        return breedId == null ? null : BreedConverter.toModel(breedServiceI.getById(breedId));
    }
}