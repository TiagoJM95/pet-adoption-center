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
        @UniqueConstraint(columnNames = {"user_id", "pet_id", "org_id"})
})
public class Interest {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime timestamp;

    private LocalDateTime reviewTimestamp;
}