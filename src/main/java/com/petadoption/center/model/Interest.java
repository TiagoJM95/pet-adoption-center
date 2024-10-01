package com.petadoption.center.model;

import com.petadoption.center.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime reviewTimestamp;

    @OneToOne(fetch = FetchType.EAGER)
    private AdoptionForm adoptionForm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interest interest = (Interest) o;
        return Objects.equals(id, interest.id) && Objects.equals(user, interest.user) && Objects.equals(pet, interest.pet) && Objects.equals(organization, interest.organization) && status == interest.status && Objects.equals(createdAt, interest.createdAt) && Objects.equals(reviewTimestamp, interest.reviewTimestamp) && Objects.equals(adoptionForm, interest.adoptionForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, pet, organization, status, createdAt, reviewTimestamp, adoptionForm);
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}