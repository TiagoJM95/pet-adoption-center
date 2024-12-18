package com.petadoption.center.service;

import com.petadoption.center.converter.*;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.not_found.PetNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Color;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Pet;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.interfaces.*;
import com.petadoption.center.specifications.PetSearchCriteria;
import com.petadoption.center.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.EnumConverter.convertListOfStringToEnum;
import static com.petadoption.center.converter.EnumConverter.convertStringToEnum;
import static com.petadoption.center.specifications.PetSpecifications.*;
import static com.petadoption.center.util.Messages.*;
import static java.lang.String.format;

@Service
@CacheConfig(cacheNames = "pet")
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
    @Cacheable(key = "#id")
    public PetGetDto getById(String id) {
        return PetConverter.toDto(findById(id));
    }

    @Override
    public List<PetGetDto> searchPets(PetSearchCriteria criteria, Pageable pageable) {
        Specification<Pet> filters = (criteria == null) ? Specification.where(null) : buildFilters(criteria);
        return petRepository.findAll(filters, pageable).stream().map(PetConverter::toDto).toList();
    }

    @Override
    public PetGetDto create(PetCreateDto dto) {
        breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
        Pet pet = buildPetFromDto(dto);
        return PetConverter.toDto(petRepository.save(pet));
    }

    @Transactional
    @Override
    public String createFromList(List<PetCreateDto> dtoList) {
        for (PetCreateDto dto : dtoList) {
            breedServiceI.verifyIfBreedsAndSpeciesMatch(dto);
            Pet pet = buildPetFromDto(dto);
            petRepository.save(pet);
        }
        return PET_LIST_ADDED_SUCCESS;
    }

    @Override
    @CachePut(key = "#id")
    public PetGetDto update(String id, PetUpdateDto dto) {
        Pet pet = findById(id);
        updateFields(dto, pet);
        petRepository.save(pet);
        return PetConverter.toDto(pet);
    }

    @Override
    @CacheEvict(key = "#id")
    public String delete(String id) {
        findById(id);
        petRepository.deleteById(id);
        return format(PET_DELETE_MESSAGE, id);
    }

    private Pet findById(String id) {
        return petRepository.findById(id).orElseThrow(
                () -> new PetNotFoundException(format(PET_NOT_FOUND, id)));
    }

    private Pet buildPetFromDto(PetCreateDto dto) {
        Pet pet = PetConverter.toModel(dto);

        pet.setSpecies(SpeciesConverter.toModel(speciesServiceI.getById(dto.speciesId())));
        pet.setPrimaryBreed(BreedConverter.toModel(breedServiceI.getById(dto.primaryBreedId())));
        pet.setSecondaryBreed(getBreedOrNull(dto.secondaryBreedId()));
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
        return Specification
                .where(searchCriteria.speciesNames() != null ? filterBySpecies(searchCriteria.speciesNames()) : null)
                .and(searchCriteria.breedNames() != null ? filterByBreed(searchCriteria.breedNames()) : null)
                .and(searchCriteria.colorNames() != null ? filterByColor(searchCriteria.colorNames()) : null)
                .and(searchCriteria.coats() != null ? filterByCoat(convertListOfStringToEnum(searchCriteria.coats(), Coats.class)) : null)
                .and(searchCriteria.sizes() != null ? filterBySize(convertListOfStringToEnum(searchCriteria.sizes(), Sizes.class)) : null)
                .and(searchCriteria.ages() != null ? filterByAge(convertListOfStringToEnum(searchCriteria.ages(), Ages.class)) : null)
                .and(searchCriteria.attributes() != null ? filterByAttributes(searchCriteria.attributes()) : null)
                .and(searchCriteria.states() != null ? filterByState(searchCriteria.states()) : null)
                .and(searchCriteria.cities() != null ? filterByCity(searchCriteria.cities()) : null)
                .and(searchCriteria.gender() != null ? filterByGender(convertStringToEnum(searchCriteria.gender(), Genders.class)) : null)
                .and(searchCriteria.isAdopted() != null ? filterByIsAdopted(searchCriteria.isAdopted()) : null)
                .and(searchCriteria.isPureBreed() != null ? filterByPureBreed() : null);
    }

    private void updateFields(PetUpdateDto dto, Pet pet) {

        Organization org = dto.organizationId() != null
                ? OrganizationConverter.toModel(organizationServiceI.getById(dto.organizationId()))
                : null;
        Sizes sizes = dto.size() != null
                ? convertStringToEnum(dto.size(), Sizes.class)
                :null;
        Ages ages = dto.age() != null
                ? convertStringToEnum(dto.age(), Ages.class)
                :null;

        Utils.updateFields(sizes, pet.getSize(), pet::setSize);
        Utils.updateFields(ages, pet.getAge(), pet::setAge);
        Utils.updateFields(dto.description(), pet.getDescription(), pet::setDescription);
        Utils.updateFields(dto.imageUrl(), pet.getImageUrl(), pet::setImageUrl);
        Utils.updateFields(dto.isAdopted(), pet.isAdopted(), pet::setAdopted);
        Utils.updateFields(dto.attributes(), pet.getAttributes(), pet::setAttributes);
        Utils.updateFields(org, pet.getOrganization(), pet::setOrganization);
    }

    private Color getColorOrNull(String colorId) {
        return colorId == null ? null : ColorConverter.toModel(colorServiceI.getById(colorId));
    }

    private Breed getBreedOrNull(String breedId) {
        return breedId == null ? null : BreedConverter.toModel(breedServiceI.getById(breedId));
    }
}