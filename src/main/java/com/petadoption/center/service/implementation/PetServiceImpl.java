package com.petadoption.center.service.implementation;

import com.petadoption.center.dto.pet.PetCreateDto;
import com.petadoption.center.dto.pet.PetGetDto;
import com.petadoption.center.dto.pet.PetUpdateDto;
import com.petadoption.center.exception.breed.BreedNotFoundException;
import com.petadoption.center.exception.color.ColorNotFoundException;
import com.petadoption.center.exception.organization.OrgNotFoundException;
import com.petadoption.center.exception.pet.PetNotFoundException;
import com.petadoption.center.exception.species.SpeciesNotFoundException;
import com.petadoption.center.model.Pet;
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

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final SpeciesServiceImpl speciesRepository;
    private final BreedServiceImpl  breedRepository;
    private final ColorServiceImpl  colorRepository;
    private final OrganizationServiceImpl  organizationRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, SpeciesServiceImpl speciesRepository, BreedServiceImpl breedRepository, ColorServiceImpl colorRepository, OrganizationServiceImpl organizationRepository) {
        this.petRepository = petRepository;
        this.speciesRepository = speciesRepository;
        this.breedRepository = breedRepository;
        this.colorRepository = colorRepository;
        this.organizationRepository = organizationRepository;
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
    public PetGetDto addNewPet(PetCreateDto pet) throws OrgNotFoundException, SpeciesNotFoundException, ColorNotFoundException, BreedNotFoundException {
        return convertToPetGetDto(petRepository.save(convertToPetModel(pet)));

    }

    @Override
    public PetGetDto updatePet(Long id, PetUpdateDto pet) {
        return null;
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
                speciesRepository.findSpeciesById(pet.petSpeciesId()),
                breedRepository.findBreedById(pet.primaryBreedId()),
                breedRepository.findBreedById(pet.secondaryBreedId()),
                colorRepository.findColorById(pet.primaryColor()),
                colorRepository.findColorById(pet.secondaryColor()),
                colorRepository.findColorById(pet.tertiaryColor()),
                organizationRepository.findById(pet.organizationId())
        );
    }
}
