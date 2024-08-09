package com.petadoption.center.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "adoption_forms")
public class AdoptionForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user_id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet_id;
}
