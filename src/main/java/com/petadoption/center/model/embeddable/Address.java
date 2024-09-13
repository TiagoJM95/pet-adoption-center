package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    private String street;
    private String city;
    private String state;
    private String postalCode;
}
