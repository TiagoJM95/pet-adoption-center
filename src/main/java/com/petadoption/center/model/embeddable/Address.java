package com.petadoption.center.model.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.petadoption.center.util.Messages.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    @NotBlank(message = BLANK_FIELD)
    @Pattern(regexp = "[a-zA-Z_0-9, .-]+", message = STREET_CHARACTERS)
    private String street;

    @NotBlank(message = BLANK_FIELD)
    @Pattern(regexp = "[a-zA-Z]+", message = ONLY_LETTERS)
    private String city;

    @NotBlank(message = BLANK_FIELD)
    @Pattern(regexp = "[a-zA-Z ]+", message = ONLY_LETTERS)
    private String state;

    @NotBlank(message = BLANK_FIELD)
    @Size(max = 9, message = POSTAL_CODE_SIZE)
    @Pattern(regexp = "[0-9]{4}-[0-9]{3}", message = POSTAL_CODE_FORMAT)
    private String postalCode;
}