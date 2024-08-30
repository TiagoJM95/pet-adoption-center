package com.petadoption.center.dto.organization;

public sealed interface OrgDto permits OrgCreateDto, OrgUpdateDto {
    String name();
    String email();
    String phoneNumber();
    String street();
    String city();
    String state();
    String postalCode();
    String websiteUrl();
    String facebook();
    String instagram();
    String twitter();
    String youtube();
}