package com.petadoption.center.service.implementation;

import com.petadoption.center.converter.*;
import com.petadoption.center.dto.breed.BreedGetDto;
import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.petSearchCriteria.PetSearchCriteria;
import com.petadoption.center.exception.breed.BreedMismatchException;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.InvalidDescriptionException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Breed;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Pet;
import com.petadoption.center.model.Species;
import com.petadoption.center.repository.PetRepository;
import com.petadoption.center.service.*;
import com.petadoption.center.util.aggregator.PetCreateContext;
import com.petadoption.center.util.aggregator.PetGetContext;
import com.petadoption.center.util.factory.AttributesFactory;
import io.micrometer.common.util.StringUtils;
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
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesService speciesService;
    private final BreedService breedService;
    private final ColorService colorService;
    private final OrganizationService organizationService;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, SpeciesServiceImpl speciesService, BreedServiceImpl breedService, ColorServiceImpl colorService, OrganizationServiceImpl organizationService) {
        this.petRepository = petRepository;
        this.speciesService = speciesService;
        this.breedService = breedService;
        this.colorService = colorService;
        this.organizationService = organizationService;
    }

    @Override
    public PetGetDto getPetById(String id) throws PetNotFoundException {
        return PetConverter.toDto(findPetById(id), buildGetContext(findPetById(id)));
    }

    @Override
    public List<PetGetDto> searchPets(PetSearchCriteria criteria, int page, int size, String sortBy) throws SpeciesNotFoundException, InvalidDescriptionException {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        Specification<Pet> filters = buildFilters(criteria);
        return petRepository.findAll(filters, pageRequest).stream().map(pet -> PetConverter.toDto(pet, buildGetContext(pet))).toList();
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto dto) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException, InvalidDescriptionException {
        breedService.verifyIfBreedsAndSpeciesMatch(dto);
        Pet p = PetConverter.toModel(dto, buildCreateContext(dto));
        Pet pet = petRepository.save(p);
        return PetConverter.toDto(pet, buildGetContext(pet));
    }

    @Override
    public void addListOfNewPets(List<PetCreateDto> pets) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, BreedMismatchException, InvalidDescriptionException {
        for (PetCreateDto dto : pets) {
            breedService.verifyIfBreedsAndSpeciesMatch(dto);
        }
        for (PetCreateDto dto : pets) {
            petRepository.save(PetConverter.toModel(dto, buildCreateContext(dto)));
        }
    }

    @Override
    public PetGetDto updatePet(String id, PetUpdateDto dto) throws PetNotFoundException, OrgNotFoundException, InvalidDescriptionException {
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

    private PetCreateContext buildCreateContext(PetCreateDto pet) throws SpeciesNotFoundException, BreedNotFoundException, ColorNotFoundException, OrgNotFoundException, InvalidDescriptionException{

        Species species = SpeciesConverter.toModel(speciesService.getSpeciesById(pet.petSpeciesId()));
        Breed primaryBreed = BreedConverter.toModel(breedService.getBreedById(pet.primaryBreedId()), species);
        Breed secondaryBreed = pet.secondaryBreedId().equals("NONE")
                ? null
                :  BreedConverter.toModel(breedService.getBreedById(pet.secondaryBreedId()), species);


        return PetCreateContext.builder()
                .species(species)
                .primaryBreed(primaryBreed)
                .secondaryBreed(secondaryBreed)
                .primaryColor(ColorConverter.toModel(colorService.getColorById(pet.primaryColor())))
                .secondaryColor(pet.secondaryColor().equals("NONE")
                        ? null
                        : ColorConverter.toModel(colorService.getColorById(pet.secondaryColor())))
                .tertiaryColor(pet.tertiaryColor().equals("NONE")
                        ? null
                        : ColorConverter.toModel(colorService.getColorById(pet.tertiaryColor())))
                .organization(OrgConverter.toModel(organizationService.getOrganizationById(pet.organizationId())))
                .gender(getGenderByDescription(pet.gender()))
                .coat(getCoatByDescription(pet.coat()))
                .size(getSizeByDescription(pet.size()))
                .age(getAgeByDescription(pet.age()))
                .attributes(AttributesFactory.create(pet))
                .build();
    }

    private Specification<Pet> buildFilters(PetSearchCriteria searchCriteria) throws InvalidDescriptionException {
        return Specification.where(
                        StringUtils.isBlank(searchCriteria.nameLike()) ? null : nameLike(searchCriteria.nameLike().toLowerCase()))
                        .and(searchCriteria.species() == null ? null : findBySpecies(searchCriteria.species()))
                        .and(searchCriteria.state() == null ? null : findByState(searchCriteria.state()))
                        .and(searchCriteria.city() == null ? null : findByCity(searchCriteria.city()))
                        .and(searchCriteria.breed() == null ? null : findByBreed((searchCriteria.breed()))
                        .and(searchCriteria.color() == null ? null : findByColor(searchCriteria.color()))
                        .and(searchCriteria.gender() == null ? null : findByGender(getGenderByDescription(searchCriteria.gender())))
                        .and(searchCriteria.coat() == null ? null : findByCoat(getCoatByDescription(searchCriteria.coat())))
                        .and(searchCriteria.size() == null ? null : findBySize(getSizeByDescription(searchCriteria.size())))
                        .and(searchCriteria.age() == null ? null : findByAge(getAgeByDescription(searchCriteria.age())))
                        .and(searchCriteria.isAdopted() == null ? null : isAdopted(searchCriteria.isAdopted()))
                        .and(searchCriteria.isSterilized() == null ? null : isSterilized(searchCriteria.isSterilized()))
                        .and(searchCriteria.isVaccinated() == null ? null : isVaccinated(searchCriteria.isVaccinated()))
                        .and(searchCriteria.isChipped() == null ? null : isChipped(searchCriteria.isChipped()))
                        .and(searchCriteria.isSpecialNeeds() == null ? null : isSpecialNeeds(searchCriteria.isSpecialNeeds()))
                        .and(searchCriteria.isHouseTrained() == null ? null : isHouseTrained(searchCriteria.isHouseTrained()))
                        .and(searchCriteria.goodWithKids() == null ? null : isGoodWithKids(searchCriteria.goodWithKids()))
                        .and(searchCriteria.goodWithDogs() == null ? null : isGoodWithDogs(searchCriteria.goodWithDogs()))
                        .and(searchCriteria.goodWithCats() == null ? null : isGoodWithCats(searchCriteria.goodWithCats()))
                        .and(searchCriteria.isPureBreed() == null ? null : findByPureBreed(searchCriteria.isPureBreed()))
                );
    }

    private void updatePetFields(PetUpdateDto dto, Pet pet) throws OrgNotFoundException, InvalidDescriptionException {
        Organization org = OrgConverter.toModel(organizationService.getOrganizationById(dto.organizationId()));

        updateFields(getSizeByDescription(dto.size()), pet.getSize(), pet::setSize);
        updateFields(getAgeByDescription(dto.age()), pet.getAge(), pet::setAge);
        updateFields(dto.description(), pet.getDescription(), pet::setDescription);
        updateFields(dto.imageUrl(), pet.getImageUrl(), pet::setImageUrl);
        updateFields(dto.isAdopted(), pet.getIsAdopted(), pet::setIsAdopted);
        updateFields(AttributesFactory.create(dto), pet.getAttributes(), pet::setAttributes);
        updateFields(org, pet.getOrganization(), pet::setOrganization);
    }
}