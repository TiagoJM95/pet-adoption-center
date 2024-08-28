package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetDuplicateImageException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.Pet;
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
import static com.petadoption.center.specifications.PetSpecifications.nameLike;
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
    public List<PetGetDto> getAllPets(String nameLikeFilter) {
        Specification<Pet> filters = Specification.where(StringUtils.isBlank(nameLikeFilter) ? null : nameLike(nameLikeFilter));
        return petRepository.findAll(filters).stream().map(this::convertToPetGetDto).toList();
    }

    @Override
    public PetGetDto getPetById(Long id) throws PetNotFoundException {
        return convertToPetGetDto(findById(id));
    }

    @Override
    public PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException, PetDuplicateImageException {
        checkIfPetExistsByImageUrl(pet.imageUrl());
        return convertToPetGetDto(petRepository.save(convertToPetModel(pet)));
    }

    @Override
    public PetGetDto updatePet(Long id, PetUpdateDto pet) throws PetNotFoundException, PetDuplicateImageException, OrgNotFoundException {
        Pet petToUpdate = findById(id);
        checkIfPetExistsByImageUrl(pet.imageUrl());
        Organization org = organizationService.findById(pet.organizationId());
    }

    private void updatePetFields(PetUpdateDto pet, Pet petToUpdate) {
        updateIfChanged(pet::size, petToUpdate::getSize, petToUpdate::setSize);
        updateIfChanged(pet::age, petToUpdate::getAge, petToUpdate::setAge);
        updateIfChanged(pet::description, petToUpdate::getDescription, petToUpdate::setDescription);
        updateIfChanged(pet::imageUrl, petToUpdate::getImageUrl, petToUpdate::setImageUrl);
        updateIfChanged(pet::isAdopted, petToUpdate::getIsAdopted, petToUpdate::setIsAdopted);
        updateIfChanged(() -> new Attributes(pet.sterilized(), pet.vaccinated(), pet.chipped(), pet.specialNeeds(), pet.houseTrained(), pet.goodWithKids(), pet.goodWithDogs(), pet.goodWithCats()), petToUpdate::getAttributes, petToUpdate::setAttributes);
        updateIfChanged(pet::organizationId, () -> petToUpdate.getOrganization().getId(), () -> petToUpdate.setOrganization(organizationService.findById(pet.organizationId())));
    }

    private Pet findById(Long id) throws PetNotFoundException {
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

    private void checkIfPetExistsByImageUrl(String imageUrl) throws PetDuplicateImageException {
        if (petRepository.findByImageUrl(imageUrl).isPresent()) {
            throw new PetDuplicateImageException("imageUrl");
        }
    }
}
