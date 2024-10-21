package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

import static com.petadoption.center.util.Messages.*;
import static com.petadoption.center.util.Regex.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address implements Serializable {

    @NotBlank(message = BLANK_FIELD)
    @Pattern(regexp = ADDRESS_NAME_REGEX, message = STREET_CHARACTERS)
    private String street;

    @NotBlank(message = BLANK_FIELD)
    @Pattern(regexp = ORG_NAME_REGEX, message = ONLY_LETTERS)
    private String city;

    @NotBlank(message = BLANK_FIELD)
    @Pattern(regexp = ORG_NAME_REGEX, message = ONLY_LETTERS)
    private String state;

    @NotBlank(message = BLANK_FIELD)
    @Size(max = 9, message = POSTAL_CODE_SIZE)
    @Pattern(regexp = POSTAL_CODE_REGEX, message = POSTAL_CODE_FORMAT)
    private String postalCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, postalCode);
    }
}