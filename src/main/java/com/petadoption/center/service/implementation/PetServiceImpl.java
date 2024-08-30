package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.dto.petSearchCriteria.PetSearchCriteria;
import com.petadoption.center.enums.Ages;
import com.petadoption.center.enums.Coats;
import com.petadoption.center.enums.Genders;
import com.petadoption.center.enums.Sizes;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDuplicateImageException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.*;
import com.petadoption.center.model.embeddable.Attributes;
import com.petadoption.center.repository.*;
import com.petadoption.center.service.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.petadoption.center.converter.BreedConverter.fromModelToBreedGetDto;
import static com.petadoption.center.converter.ColorConverter.fromModelToColorGetDto;
import static com.petadoption.center.converter.OrgConverter.fromModelToOrgGetDto;
import static com.petadoption.center.converter.PetConverter.fromModelToPetGetDto;
import static com.petadoption.center.converter.PetConverter.fromPetCreateDtoToModel;
import static com.petadoption.center.converter.SpeciesConverter.fromModelToSpeciesGetDto;
import static com.petadoption.center.enums.Ages.getAgeByDescription;
import static com.petadoption.center.enums.Coats.getCoatByDescription;
import static com.petadoption.center.enums.Genders.getGenderByDescription;
import static com.petadoption.center.enums.Sizes.getSizeByDescription;
import static com.petadoption.center.specifications.PetSpecifications.*;
import static com.petadoption.center.util.FieldUpdater.updateIfChanged;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesServiceImpl speciesService;
    private final BreedServiceImpl breedService;
    private final ColorServiceImpl colorService;
    private final OrganizationServiceImpl organizationService;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, SpeciesServiceImpl speciesService, BreedServiceImpl breedService, ColorServiceImpl colorService, OrganizationServiceImpl organizationService) {
        this.petRepository = petRepository;
        this.speciesService = speciesService;
        this.breedService = breedService;
        this.colorService = colorService;
        this.organizationService = organizationService;
    }

    @Override
    public PetGetDto getPetById(Long id) throws PetNotFoundException {
        return convertToPetGetDto(findById(id));
    }

    @Override
    public List<PetGetDto> searchPets(PetSearchCriteria searchCriteria, String species, String state, String city) throws SpeciesNotFoundException {
        Specification<Pet> filters = buildFilters(searchCriteria, speciesService.findSpeciesByName(species), state, city);
        return petRepository.findAll(filters).stream().map(this::convertToPetGetDto).toList();
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateImageException {
        //checkIfPetExistsByImageUrl(pet.imageUrl());
        return convertToPetGetDto(petRepository.save(convertToPetModel(pet)));
    }

    @Override
    public void addListOfNewPets(List<PetCreateDto> pets) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateImageException {
        for (PetCreateDto pet : pets) {
            //checkIfPetExistsByImageUrl(pet.imageUrl());
            petRepository.save(convertToPetModel(pet));
        }
    }

    @Override
    public PetGetDto updatePet(Long id, PetUpdateDto pet) throws PetNotFoundException, PetDuplicateImageException, OrgNotFoundException {
        Pet petToUpdate = findById(id);
        if(!pet.imageUrl().equals(petToUpdate.getImageUrl())) {
            checkIfPetExistsByImageUrl(pet.imageUrl());
        }
        updatePetFields(pet, petToUpdate);
        return convertToPetGetDto(petRepository.save(petToUpdate));
    }

    Pet findById(Long id) throws PetNotFoundException {
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("id"));
    }

    private PetGetDto convertToPetGetDto(Pet pet) {
        return fromModelToPetGetDto(
                pet,
                fromModelToOrgGetDto(pet.getOrganization()),
                fromModelToSpeciesGetDto(pet.getSpecies()),
                fromModelToBreedGetDto(pet.getPrimaryBreed()),
                fromModelToBreedGetDto(pet.getSecondaryBreed()),
                fromModelToColorGetDto(pet.getPrimaryColor()),
                fromModelToColorGetDto(pet.getSecondaryColor()),
                fromModelToColorGetDto(pet.getTertiaryColor())
        );
    }

    private Pet convertToPetModel(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException {
        return fromPetCreateDtoToModel(
                pet,
                speciesService.findSpeciesById(pet.petSpeciesId()),
                breedService.findBreedById(pet.primaryBreedId()),
                breedService.findBreedById(pet.secondaryBreedId()),
                colorService.findColorById(pet.primaryColor()),
                colorService.findColorById(pet.secondaryColor()),
                colorService.findColorById(pet.tertiaryColor()),
                organizationService.findById(pet.organizationId())
        );
    }

    private Specification<Pet> buildFilters(PetSearchCriteria searchCriteria, Species species, String state, String city) {

        Genders gender = getGenderByDescription(searchCriteria.gender()).orElseThrow(() -> new IllegalArgumentException(""));
        Coats coat = getCoatByDescription(searchCriteria.coat()).orElseThrow(() -> new IllegalArgumentException(""));
        Sizes size = getSizeByDescription(searchCriteria.size()).orElseThrow(() -> new IllegalArgumentException(""));
        Ages age = getAgeByDescription(searchCriteria.age()).orElseThrow(() -> new IllegalArgumentException(""));

        return Specification.where(
                        StringUtils.isBlank(searchCriteria.nameLike()) ? null : nameLike(searchCriteria.nameLike().toLowerCase()))
                .and(species == null ? null : findBySpecies(species))
                .and(state == null ? null : findByState(state))
                .and(city == null ? null : findByCity(city))
                .and(searchCriteria.breed() == null ? null : findByBreed((searchCriteria.breed()))
                        .and(searchCriteria.color() == null ? null : findByColor(searchCriteria.color()))
                        .and(searchCriteria.gender() == null ? null : findByGender(gender))
                        .and(searchCriteria.coat() == null ? null : findByCoat(coat))
                        .and(searchCriteria.size() == null ? null : findBySize(size))
                        .and(searchCriteria.age() == null ? null : findByAge(age))
                        .and(searchCriteria.isAdopted() == null ? null : isAdopted(searchCriteria.isAdopted()))
                        .and(searchCriteria.isSterilized() == null ? null : isSterilized(searchCriteria.isSterilized()))
                        .and(searchCriteria.isVaccinated() == null ? null : isVaccinated(searchCriteria.isVaccinated()))
                        .and(searchCriteria.isChipped() == null ? null : isChipped(searchCriteria.isChipped()))
                        .and(searchCriteria.isSpecialNeeds() == null ? null : isSpecialNeeds(searchCriteria.isSpecialNeeds()))
                        .and(searchCriteria.isHouseTrained() == null ? null : isHouseTrained(searchCriteria.isHouseTrained()))
                        .and(searchCriteria.goodWithKids() == null ? null : isGoodWithKids(searchCriteria.goodWithKids()))
                        .and(searchCriteria.goodWithDogs() == null ? null : isGoodWithDogs(searchCriteria.goodWithDogs()))
                        .and(searchCriteria.goodWithCats() == null ? null : isGoodWithCats(searchCriteria.goodWithCats()))
                );
    }

    private void checkIfPetExistsByImageUrl(String imageUrl) throws PetDuplicateImageException {
        if (petRepository.findByImageUrl(imageUrl).isPresent()) {
            throw new PetDuplicateImageException("imageUrl");
        }
    }

    private void updatePetFields(PetUpdateDto pet, Pet petToUpdate) throws OrgNotFoundException {
        updateIfChanged(pet::size, petToUpdate::getSize, petToUpdate::setSize);
        updateIfChanged(pet::age, petToUpdate::getAge, petToUpdate::setAge);
        updateIfChanged(pet::description, petToUpdate::getDescription, petToUpdate::setDescription);
        updateIfChanged(pet::imageUrl, petToUpdate::getImageUrl, petToUpdate::setImageUrl);
        updateIfChanged(pet::isAdopted, petToUpdate::getIsAdopted, petToUpdate::setIsAdopted);
        updateIfChanged(() -> new Attributes(pet.sterilized(), pet.vaccinated(), pet.chipped(), pet.specialNeeds(), pet.houseTrained(), pet.goodWithKids(), pet.goodWithDogs(), pet.goodWithCats()), petToUpdate::getAttributes, petToUpdate::setAttributes);
        Organization org = organizationService.findById(pet.organizationId());
        if(pet.organizationId() != null && !org.equals(petToUpdate.getOrganization())) {
            petToUpdate.setOrganization(org);
        }
    }

}
