package com.petadoption.center.converter;

import com.petadoption.center.dto.organization.OrgCreateDto;
import com.petadoption.center.dto.organization.OrgGetDto;
import com.petadoption.center.model.Organization;
import com.petadoption.center.model.embeddable.Address;
import com.petadoption.center.model.embeddable.SocialMedia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class OrgConverterTest {


    @Test
    @DisplayName("Test OrgCreateDto to Org model is working correctly")
    void fromOrgCreateDtoToModel() {
        OrgCreateDto orgCreateDto = new OrgCreateDto(
                "Pet Adoption Center",
                "petcenter@email.com",
                "227778899",
                "Rua do pet, 123",
                "Gondomar",
                "Porto",
                "4100-123",
                "www.petcenter.pt",
                "www.facebook.com/petCenter",
                "@petCenter",
                "petCenter",
                "www.youtube.com/petCenter"
                );
        Organization org = OrgConverter.fromOrgCreateDtoToModel(orgCreateDto);

        assertEquals("Pet Adoption Center", org.getName());
        assertEquals("petcenter@email.com", org.getEmail());
        assertEquals("227778899", org.getPhoneNumber());
        assertEquals("Rua do pet, 123", org.getAddress().getStreet());
        assertEquals("Gondomar", org.getAddress().getCity());
        assertEquals("Porto", org.getAddress().getState());
        assertEquals("4100-123", org.getAddress().getPostalCode());
        assertEquals("www.petcenter.pt", org.getWebsiteUrl());
        assertEquals("www.facebook.com/petCenter", org.getSocialMedia().getFacebook());
        assertEquals("@petCenter", org.getSocialMedia().getInstagram());
        assertEquals("petCenter", org.getSocialMedia().getTwitter());
        assertEquals("www.youtube.com/petCenter", org.getSocialMedia().getYoutube());
    }



    @Test
    @DisplayName("Test Org model to OrgGetDto is working correctly")
    void fromModelToOrgGetDto() {

        Organization org = new Organization();
        org.setId(1L);
        org.setName("Pet Adoption Center");
        org.setEmail("petcenter@email.com");
        org.setPhoneNumber("227778899");
        org.setAddress(new Address("Rua do pet, 123", "Gondomar", "Porto", "4100-123"));
        org.setWebsiteUrl("www.petcenter.pt");
        org.setSocialMedia(new SocialMedia("www.facebook.com/petCenter", "@petCenter", "petCenter", "www.youtube.com/petCenter"));
        OrgGetDto orgGetDto = OrgConverter.fromModelToOrgGetDto(org);

        assertEquals(1L, orgGetDto.id());
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
