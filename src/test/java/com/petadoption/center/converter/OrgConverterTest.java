package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OrgConverterTest {

    private Address createAddress;
    private Address updateAddress;
    private SocialMedia socialMedia;
    private OrgCreateDto orgCreateDto;
    private OrgGetDto orgGetDto;
    private Organization org;

    @BeforeEach
    void setUp() {
        createAddress = new Address("Rua das Andorinhas, 123",
                "Vila Nova de Gaia",
                "Porto",
                "4410-000");

        updateAddress = new Address("Rua dos bandidos, 123",
                "Rio Tinto",
                "Porto",
                "4100-001");

        socialMedia = new SocialMedia(
                "www.facebook.com/petCenter",
                "@petCenter",
                "petCenter",
                "www.youtube.com/petCenter"
        );

        orgCreateDto = new OrgCreateDto(
                "Pet Adoption Center",
                "petcenter@email.com",
                "227778899",
                "229876567",
                createAddress,
                "www.petcenter.com",
                socialMedia
        );

        orgGetDto = new OrgGetDto(
                "1111-3333-2222",
                "Pet Adoption Center",
                "petcenter@email.com",
                "227778899",
                "229876567",
                createAddress,
                "www.petcenter.com",
                socialMedia
        );

        org = new Organization();
        org.setId("1111-3333-2222");
        org.setName("Pet Adoption Center");
        org.setEmail("petcenter@email.com");
        org.setPhoneNumber("227778899");
        org.setAddress(new Address("Rua do pet, 123", "Gondomar", "Porto", "4100-123"));
        org.setWebsiteUrl("www.petcenter.pt");
        org.setSocialMedia(new SocialMedia("www.facebook.com/petCenter", "@petCenter", "petCenter", "www.youtube.com/petCenter"));

    }


    @Test
    @DisplayName("Test OrgCreateDto to Org model is working correctly")
    void fromOrgCreateDtoToModel() {
        Organization org = OrgConverter.toModel(orgCreateDto);

        assertEquals("Pet Adoption Center", org.getName());
        assertEquals("petcenter@email.com", org.getEmail());
        assertEquals("227778899", org.getNif());
        assertEquals("229876567", org.getPhoneNumber());
        assertEquals("Rua das Andorinhas, 123", org.getAddress().getStreet());
        assertEquals("Vila Nova de Gaia", org.getAddress().getCity());
        assertEquals("Porto", org.getAddress().getState());
        assertEquals("4410-000", org.getAddress().getPostalCode());
        assertEquals("www.petcenter.com", org.getWebsiteUrl());
        assertEquals("www.facebook.com/petCenter", org.getSocialMedia().getFacebook());
        assertEquals("@petCenter", org.getSocialMedia().getInstagram());
        assertEquals("petCenter", org.getSocialMedia().getTwitter());
        assertEquals("www.youtube.com/petCenter", org.getSocialMedia().getYoutube());
    }

    @Test
    @DisplayName("Test OrgGetDto to Org model is working correctly")
    void fromOrgGetDtoToModel() {
        Organization org = OrgConverter.toModel(orgGetDto);
        assertEquals("1111-3333-2222", org.getId());
        assertEquals("Pet Adoption Center", org.getName());
        assertEquals("petcenter@email.com", org.getEmail());
        assertEquals("227778899", org.getNif());
        assertEquals("229876567", org.getPhoneNumber());
        assertEquals("Rua das Andorinhas, 123", org.getAddress().getStreet());
        assertEquals("Vila Nova de Gaia", org.getAddress().getCity());
        assertEquals("Porto", org.getAddress().getState());
        assertEquals("4410-000", org.getAddress().getPostalCode());
        assertEquals("www.petcenter.com", org.getWebsiteUrl());
        assertEquals("www.facebook.com/petCenter", org.getSocialMedia().getFacebook());
        assertEquals("@petCenter", org.getSocialMedia().getInstagram());
        assertEquals("petCenter", org.getSocialMedia().getTwitter());
        assertEquals("www.youtube.com/petCenter", org.getSocialMedia().getYoutube());
    }


    @Test
    @DisplayName("Test Org model to OrgGetDto is working correctly")
    void fromModelToOrgGetDto() {

        OrgGetDto orgGetDto = OrgConverter.toDto(org);

        assertEquals("1111-3333-2222", orgGetDto.id());
        assertEquals("Pet Adoption Center", orgGetDto.name());
        assertEquals("petcenter@email.com", orgGetDto.email());
        assertEquals("227778899", orgGetDto.phoneNumber());
        assertEquals("Rua do pet, 123", orgGetDto.address().getStreet());
        assertEquals("Gondomar", orgGetDto.address().getCity());
        assertEquals("Porto", orgGetDto.address().getState());
        assertEquals("4100-123", orgGetDto.address().getPostalCode());
        assertEquals("www.petcenter.pt", orgGetDto.websiteUrl());
        assertEquals("www.facebook.com/petCenter", orgGetDto.socialMedia().getFacebook());
        assertEquals("@petCenter", orgGetDto.socialMedia().getInstagram());
        assertEquals("petCenter", orgGetDto.socialMedia().getTwitter());
        assertEquals("www.youtube.com/petCenter", orgGetDto.socialMedia().getYoutube());
    }
}
