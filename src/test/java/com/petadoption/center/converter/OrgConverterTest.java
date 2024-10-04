package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.model.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.petadoption.center.testUtils.TestDtoFactory.orgCreateDto;
import static com.petadoption.center.testUtils.TestDtoFactory.orgGetDto;
import static com.petadoption.center.testUtils.TestEntityFactory.createOrganization;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OrgConverterTest {


    private OrgCreateDto orgCreateDto;
    private OrgGetDto orgGetDto;
    private Organization org;

    @BeforeEach
    void setUp() {
        orgCreateDto = orgCreateDto();
        orgGetDto = orgGetDto();
        org = createOrganization();
    }


    @Test
    @DisplayName("Test OrgCreateDto to Org model is working correctly")
    void fromOrgCreateDtoToModel() {
        Organization org = OrgConverter.toModel(orgCreateDto);

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
    @DisplayName("Test OrgGetDto to Org model is working correctly")
    void fromOrgGetDtoToModel() {
        Organization org = OrgConverter.toModel(orgGetDto);

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
    @DisplayName("Test Org model to OrgGetDto is working correctly")
    void fromModelToOrgGetDto() {

        OrgGetDto orgGetDto = OrgConverter.toDto(org);

        assertEquals("777777-77777777-7777", orgGetDto.id());
        assertEquals("Pet Adoption Center", orgGetDto.name());
        assertEquals("org@email.com", orgGetDto.email());
        assertEquals("123456789", orgGetDto.phoneNumber());
        assertEquals("Rua de Santo Antonio, 123", orgGetDto.address().getStreet());
        assertEquals("Gondomar", orgGetDto.address().getCity());
        assertEquals("Porto", orgGetDto.address().getState());
        assertEquals("4444-444", orgGetDto.address().getPostalCode());
        assertEquals("https://www.org.com", orgGetDto.websiteUrl());
        assertEquals("https://www.facebook.com", orgGetDto.socialMedia().getFacebook());
        assertEquals("https://www.instagram.com", orgGetDto.socialMedia().getInstagram());
        assertEquals("https://www.twitter.com", orgGetDto.socialMedia().getTwitter());
        assertEquals("https://www.youtube.com", orgGetDto.socialMedia().getYoutube());
    }

    @Test
    @DisplayName("Test OrgCreateDto return null if received null dto")
    void testIfFromOrgCreateDtoReturnNullIfReceivedNullDto() {
        assertNull(OrgConverter.toModel((OrgCreateDto) null));
    }

    @Test
    @DisplayName("Test OrgGetDto return null if received null dto")
    void testIfFromOrgGetDtoReturnNullIfReceivedNullDto() {
        assertNull(OrgConverter.toModel((OrgGetDto) null));
    }

    @Test
    @DisplayName("Test Org model return null if received null model")
    void testIfFromModelReturnNullIfReceivedNullModel() {
        assertNull(OrgConverter.toDto(null));
    }
}
