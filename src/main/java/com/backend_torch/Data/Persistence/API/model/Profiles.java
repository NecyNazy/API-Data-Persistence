package com.backend_torch.Data.Persistence.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "profiles",
        indexes = {
                @Index(name = "idx_profiles_name", columnList = "name"),
                @Index(name = "idx_profiles_created_at", columnList = "created_at"),
                @Index(name = "idx_profiles_gender", columnList = "gender"),
                @Index(name = "idx_profiles_age_group", columnList = "age_group"),
                @Index(name = "idx_profiles_country_id", columnList = "country_id") // FIXED
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "profile_name", columnNames = "name")
        }
)
public class Profiles {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "gender_probability")
    private Double genderProbability;
    @Column(name = "sample_size")
    private Long sampleSize;

    @Column(name = "age")
    private Integer age;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "country_probability")
    private Double countryProbability;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now(); // UTC
        }
    }
}