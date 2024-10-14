package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrganizationCreateDto;
import com.petadoption.center.dto.organization.OrganizationGetDto;
import com.petadoption.center.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.petadoption.center.testUtils.TestDtoFactory.orgGetDto;
import static com.petadoption.center.testUtils.TestDtoFactory.organizationCreateDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createOrganization;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OrganizationConverterTest {


    private OrganizationCreateDto organizationCreateDto;
    private OrganizationGetDto organizationGetDto;
    private Organization org;

    @BeforeEach
    void setUp() {
        organizationCreateDto = organizationCreateDto();
        organizationGetDto = orgGetDto();
        org = createOrganization();
    }


    @Test
    @DisplayName("Test OrganizationCreateDto to Org model is working correctly")
    void fromOrgCreateDtoToModel() {
        Organization org = OrganizationConverter.toModel(organizationCreateDto);

        assertEquals("Pet Adoption Center", org.getName());
        assertEquals("org@email.com", org.getEmail());
        assertEquals("123456789", org.getNif());
        assertEquals("123456789", org.getPhoneNumber());
        assertEquals("Rua de Santo Antonio, 123", org.getAddress().getStreet());
        assertEquals("Gondomar", org.getAddress().getCity());
        assertEquals("Porto", org.getAddress().getState());
        assertEquals("4444-444", org.getAddress().getPostalCode());
        assertEquals("https://www.org.com", org.getWebsiteUrl());
        assertEquals("https://www.facebook.com", org.getSocialMedia().getFacebook());
        assertEquals("https://www.instagram.com", org.getSocialMedia().getInstagram());
        assertEquals("https://www.twitter.com", org.getSocialMedia().getTwitter());
        assertEquals("https://www.youtube.com", org.getSocialMedia().getYoutube());
    }

    @Test
    @DisplayName("Test OrganizationGetDto to Org model is working correctly")
    void fromOrgGetDtoToModel() {
        Organization org = OrganizationConverter.toModel(organizationGetDto);

        assertEquals("777777-77777777-7777", org.getId());
        assertEquals("Pet Adoption Center", org.getName());
        assertEquals("org@email.com", org.getEmail());
        assertEquals("123456789", org.getNif());
        assertEquals("123456789", org.getPhoneNumber());
        assertEquals("Rua de Santo Antonio, 123", org.getAddress().getStreet());
        assertEquals("Gondomar", org.getAddress().getCity());
        assertEquals("Porto", org.getAddress().getState());
        assertEquals("4444-444", org.getAddress().getPostalCode());
        assertEquals("https://www.org.com", org.getWebsiteUrl());
        assertEquals("https://www.facebook.com", org.getSocialMedia().getFacebook());
        assertEquals("https://www.instagram.com", org.getSocialMedia().getInstagram());
        assertEquals("https://www.twitter.com", org.getSocialMedia().getTwitter());
        assertEquals("https://www.youtube.com", org.getSocialMedia().getYoutube());
    }


    @Test
    @DisplayName("Test Org model to OrganizationGetDto is working correctly")
    void fromModelToOrgGetDto() {

        OrganizationGetDto organizationGetDto = OrganizationConverter.toDto(org);

        assertEquals("777777-77777777-7777", organizationGetDto.id());
        assertEquals("Pet Adoption Center", organizationGetDto.name());
        assertEquals("org@email.com", organizationGetDto.email());
        assertEquals("123456789", organizationGetDto.phoneNumber());
        assertEquals("Rua de Santo Antonio, 123", organizationGetDto.address().getStreet());
        assertEquals("Gondomar", organizationGetDto.address().getCity());
        assertEquals("Porto", organizationGetDto.address().getState());
        assertEquals("4444-444", organizationGetDto.address().getPostalCode());
        assertEquals("https://www.org.com", organizationGetDto.websiteUrl());
        assertEquals("https://www.facebook.com", organizationGetDto.socialMedia().getFacebook());
        assertEquals("https://www.instagram.com", organizationGetDto.socialMedia().getInstagram());
        assertEquals("https://www.twitter.com", organizationGetDto.socialMedia().getTwitter());
        assertEquals("https://www.youtube.com", organizationGetDto.socialMedia().getYoutube());
    }

    @Test
    @DisplayName("Test OrganizationCreateDto return null if received null dto")
    void testIfFromOrgCreateDtoReturnNullIfReceivedNullDto() {
        assertNull(OrganizationConverter.toModel((OrganizationCreateDto) null));
    }

    @Test
    @DisplayName("Test OrganizationGetDto return null if received null dto")
    void testIfFromOrgGetDtoReturnNullIfReceivedNullDto() {
        assertNull(OrganizationConverter.toModel((OrganizationGetDto) null));
    }

    @Test
    @DisplayName("Test Org model return null if received null model")
    void testIfFromModelReturnNullIfReceivedNullModel() {
        assertNull(OrganizationConverter.toDto(null));
    }
}
