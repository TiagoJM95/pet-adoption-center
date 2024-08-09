package com.petadoption.center.dto.organization;



public record OrganizationCreateDto(

        String name,

        String email,

        String phoneNumber,

        String street,

        String city,

        String state,

        String postalCode,

        String websiteUrl,

        String facebook,

        String instagram,

        String twitter,

        String youtube


        ) {
}
