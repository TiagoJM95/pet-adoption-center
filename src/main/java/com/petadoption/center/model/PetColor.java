package com.petadoption.center.model;

import com.petadoption.center.enums.Colors;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class PetColor {
        private Colors primary;
        private Colors secondary;
        private Colors tertiary;
}
