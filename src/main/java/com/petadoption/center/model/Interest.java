package com.petadoption.center.model;

import com.petadoption.center.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "interests", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserAndPetAndOrganization", columnNames = {"user_id", "pet_id", "organization_id"})
})
public class Interest {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime timestamp;

    private LocalDateTime reviewTimestamp;
}